import com.google.common.collect.Lists;
import expression.Node;
import expression.Parser;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ComputationRequest implements Request {

  private final String input;

  public ComputationRequest(String input) {
    this.input = input;
  }

  public String process() throws IOException {
    String[] splitRequest = input.split(";");

    Map<String, List<Double>> a = parsingVariableValuesFunction(splitRequest[1]); // STEP 1
    List<List<Double>> listOfTuples = buildingOfValueTuplesFrom(new ArrayList<>(a.values()), splitRequest[0].split("_")[1]); //STEP 2
    List<String> parsedExpressions = Arrays.asList(splitRequest).subList(2, splitRequest.length);
    return String.valueOf(compute(input.split("_")[0], parsedExpressions, listOfTuples));
  }

  private List<List<Double>> buildingOfValueTuplesFrom(List<List<Double>> lists, String gridOrList) throws IOException {
    return switch (gridOrList) {
      case "GRID" -> Lists.cartesianProduct(lists);
      case "LIST" -> zip(lists);
      default -> throw new IllegalStateException("Unexpected value: " + gridOrList);
    };
  }

  private List<Double> buildList(double lower, double step, double upper) throws IOException {
    if (step <= 0) {
      throw new IOException("Step cannot be <= 0");
    } else {
/*      List<Double> tmp = new ArrayList<>();
      IntStream.rangeClosed(0, Double.valueOf((upper - lower) / step).intValue()).forEach(k -> tmp.add(lower + k * step));
      //  IntStream.rangeClosed(0, (int)((upper - lower) / step)).forEach(k -> tmp.add(lower + k * step));
      return tmp;*/

      return DoubleStream.iterate(lower, k ->  k <= upper, k -> lower+k*step).boxed().collect(Collectors.toList());

/*      for (int k = 0; lower + k * step <= upper; k++) {
        tmp.add(lower + k * step);
      }
      return tmp;
      */
    }
  }

  private Map<String, List<Double>> parsingVariableValuesFunction(String VariableValuesFunction) throws IOException {

/*    Map<String, List<Double>> dictionary = new HashMap<>();
    for (String tuple : VariableValuesFunction.split(",")) {
      String[] tmp = tuple.split(":");
      dictionary.put(tmp[0], buildList(Double.parseDouble(tmp[1]), Double.parseDouble(tmp[2]), Double.parseDouble(tmp[3])));

      Pattern.compile(",").splitAsStream(modulesToUpdate)
      Arrays.stream(input).mapToDouble(Double::valueOf).toArray();
    }*/

    return Arrays.stream(VariableValuesFunction.split(",")).map(s -> s.split(":"))
        .collect(Collectors.toMap(a -> a[0], a -> {
          try {
            return buildList(Double.parseDouble(a[1]), Double.parseDouble(a[2]), Double.parseDouble(a[3]));
          } catch (IOException e) {
            throw new UncheckedIOException(e);
          }
        }));

  }

  private static List<List<Double>> zip(List<List<Double>> listOfLists) throws IOException {
    if (!listOfLists.stream().allMatch(list -> list.size() == listOfLists.get(0).size())) {
      throw new IOException("Lists have different sizes");
    } else {
      int size = listOfLists.get(0).size();
      List<List<Double>> result = new ArrayList<>(size);
      IntStream.range(0, size).forEach(i -> result.add(
          listOfLists.stream()
              .map(list -> list.get(i))
              .collect(toList())));
      return result;
    }
  }

  private static double compute(String computationKind, List<String> expressions, List<List<Double>> listOfValues) {
    double result = 0;
    switch (computationKind) {
      case "MIN" -> {
        return expressions.stream().map(expression ->
            new Parser(expression).parse()).flatMapToDouble(node ->
            listOfValues.stream().mapToDouble(node::calculate)).min().orElseThrow(IllegalArgumentException::new);

/*        result = Double.POSITIVE_INFINITY;
        for (String expression : expressions) {
          Node node = new Parser(expression).parse();
          for (List<Double> list : listOfValues) {
            result = Math.min(node.calculate(list), result);
          }
        }*/

      }
      case "MAX" -> {

        return expressions.stream().map(expression ->
            new Parser(expression).parse()).flatMapToDouble(node ->
            listOfValues.stream().mapToDouble(node::calculate)).max().orElseThrow(IllegalArgumentException::new);

/*        result = Double.NEGATIVE_INFINITY;
        for (String expression : expressions) {
          Node node = new Parser(expression).parse();
          for (List<Double> list : listOfValues) {
            result = Math.max(node.calculate(list), result);
          }
        }*/

      }
      case "AVG" -> {

        return listOfValues.stream().mapToDouble(list ->
            new Parser(expressions.get(0)).parse().calculate(list))
              .average().orElseThrow(IllegalArgumentException::new);

/*        Node node = new Parser(expressions.get(0)).parse();
        for (List<Double> list : listOfValues) {
          result = result + node.calculate(list);
        }
        result = result / listOfValues.size(); //TODO add exception
        */
      }
      case "COUNT" -> result = listOfValues.size();
    }
    return result;
  }

}
