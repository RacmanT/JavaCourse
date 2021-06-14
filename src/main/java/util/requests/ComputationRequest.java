package util.requests;

import com.google.common.collect.Lists;
import util.expression.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ComputationRequest extends Request {

  public ComputationRequest(String input) {
    super(input);
  }

  private static List<List<Double>> zip(List<List<Double>> listOfLists) throws IllegalArgumentException {
    if (!listOfLists.stream().allMatch(list -> list.size() == listOfLists.get(0).size())) {
      throw new IllegalArgumentException("Lists have different sizes");
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

  private static double compute(String computationKind, List<String> expressions, List<List<Double>> listOfValues) throws IllegalArgumentException {
    try {
      switch (computationKind) {
        case "MIN" -> {
          return expressions.stream().map(expression ->
              new Parser(expression).parse()).flatMapToDouble(node ->
              listOfValues.stream().mapToDouble(node::calculate)).min().orElseThrow(IllegalArgumentException::new);
        }
        case "MAX" -> {
          return expressions.stream().map(expression ->
              new Parser(expression).parse()).flatMapToDouble(node ->
              listOfValues.stream().mapToDouble(node::calculate)).max().orElseThrow(IllegalArgumentException::new);
        }
        case "AVG" -> {
          return listOfValues.stream().mapToDouble(list ->
              new Parser(expressions.get(0)).parse().calculate(list))
              .average().orElseThrow(IllegalArgumentException::new);
        }
        case "COUNT" -> {
          return listOfValues.size();
        }
        default -> throw new IllegalArgumentException(computationKind + " is not a valid computation parameter");
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Number of coordinates in one or more expressions does not match the one in tuples");
    }
  }

  public String process() throws IllegalArgumentException {
    String[] splitRequest = input.split(";");

    Map<String, List<Double>> a = parsingVariableValuesFunction(splitRequest[1]); // STEP 1
    List<List<Double>> listOfTuples = buildingOfValueTuples(new ArrayList<>(a.values()), splitRequest[0].split("_")[1]); //STEP 2
    List<String> parsedExpressions = Arrays.asList(splitRequest).subList(2, splitRequest.length); //STEP3
    return String.valueOf(compute(input.split("_")[0], parsedExpressions, listOfTuples)); //STEP4
  }
  

  private Map<String, List<Double>> parsingVariableValuesFunction(String VariableValuesFunction) throws IllegalArgumentException {
    return Arrays.stream(VariableValuesFunction.split(",")).map(s ->
        s.split(":")).collect(Collectors.toMap(a -> a[0], a ->
        buildList(Double.parseDouble(a[1]), Double.parseDouble(a[2]), Double.parseDouble(a[3]))));
  }

  private List<Double> buildList(double lower, double step, double upper) throws IllegalArgumentException {
    if (step <= 0) {
      throw new IllegalArgumentException("Step cannot be zero nor negative");
    } else {
      return IntStream.rangeClosed(0, (int) ((upper - lower) / step)).mapToDouble(k -> lower + k * step).boxed().collect(Collectors.toList());
    }
  }

  private List<List<Double>> buildingOfValueTuples(List<List<Double>> lists, String gridOrList) throws IllegalArgumentException {
    return switch (gridOrList) {
      case "GRID" -> Lists.cartesianProduct(lists);
      case "LIST" -> zip(lists);
      default -> throw new IllegalArgumentException("Unexpected value: " + gridOrList + " while building coordinates tuples");
    };
  }


}