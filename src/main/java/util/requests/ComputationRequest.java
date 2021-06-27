package util.requests;

import com.google.common.collect.Lists;
import util.expression.Node;
import util.expression.Parser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author Tibor Racman
 * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class modeling the computation of mathematical expression on a set of variables
 */

public class ComputationRequest extends Request {

  public ComputationRequest(String input) {
    super(input);
  }

  public String process() throws IllegalArgumentException {
    String[] tokens = input.split(";");
    String variableValuesFunction = tokens[1];
    String gridOrList = tokens[0].split("_")[1];
    String computationKind = input.split("_")[0];

    Map<String, List<Double>> variablesMap = parsingVariableValuesFunction(variableValuesFunction); // STEP 1

    List<Map<String, Double>> listOfTuples = buildingOfValueTuples(variablesMap, gridOrList); // STEP 2

    List<Node> expressions = parseExpressions(Arrays.asList(tokens).subList(2, tokens.length)); // STEP3

    return String.valueOf(compute(computationKind, expressions, listOfTuples)); // STEP4

  }

  private static List<List<Double>> zip(List<List<Double>> listOfLists) throws IllegalArgumentException {
    if (!listOfLists.stream().allMatch(list -> list.size() == listOfLists.get(0).size())) {
      throw new IllegalArgumentException("Lists have different sizes");
    } else {
      int size = listOfLists.get(0).size();
      List<List<Double>> result = new ArrayList<>(size);
      IntStream.range(0, size).forEach(i -> result.add(listOfLists.stream().map(list -> list.get(i)).collect(toList())));
      return result;
    }
  }

  private static List<Node> parseExpressions(List<String> input) throws IllegalArgumentException {
    return input.stream().map(expression ->
        new Parser(expression).parse()).collect(Collectors.toList());
  }

  private double compute(String computationKind, List<Node> expressions, List<Map<String, Double>> coordinates) throws IllegalArgumentException {
    switch (computationKind) {
      case "COUNT" -> {
        return coordinates.size();
      }
      case "MIN" -> {
        return expressions.stream().flatMapToDouble(node ->
            coordinates.stream().mapToDouble(node::calculate)).min().orElseThrow(IllegalArgumentException::new);
      }
      case "MAX" -> {
        return expressions.stream().flatMapToDouble(node ->
            coordinates.stream().mapToDouble(node::calculate)).max().orElseThrow(IllegalArgumentException::new);
      }
      case "AVG" -> {
        return coordinates.stream().mapToDouble(list ->
            expressions.get(0).calculate(list))
            .average().orElseThrow(IllegalArgumentException::new);
      }
      default -> throw new IllegalArgumentException(computationKind + " is not a valid computation parameter");
    }
  }


  private static Map<String, List<Double>> parsingVariableValuesFunction(String VariableValuesFunction) throws IllegalArgumentException {
    return Arrays.stream(VariableValuesFunction.split(",")).map(s ->
        s.split(":")).collect(Collectors.toMap(a -> a[0], a ->
        buildingList(Double.parseDouble(a[1]), Double.parseDouble(a[2]), Double.parseDouble(a[3]))));
  }

  private static List<Double> buildingList(double lower, double step, double upper) throws IllegalArgumentException {
    if (step <= 0) {
      throw new IllegalArgumentException("Step cannot be zero nor negative");
    } else {
      return IntStream.rangeClosed(0, (int) ((upper - lower) / step)).mapToDouble(k -> lower + k * step).boxed().collect(Collectors.toList());
    }
  }

  private static List<Map<String, Double>> buildingOfValueTuples(Map<String, List<Double>> variablesMap, String gridOrList) throws IllegalArgumentException {

    List<String> variables = Lists.newArrayList(variablesMap.keySet());
    List<Map<String, Double>> result = new ArrayList<>();

    switch (gridOrList) {
      case "GRID":
        for (List<Double> list : Lists.cartesianProduct(new ArrayList<>(variablesMap.values()))) {
          Iterator<String> keyIterator = variables.iterator();
          Iterator<Double> valIterator = list.iterator();
          result.add(IntStream.range(0, list.size()).boxed()
              .collect(Collectors.toMap(i -> keyIterator.next(), i -> valIterator.next())));
        }
        break;
      case "LIST":
        for (List<Double> list : zip(new ArrayList<>(variablesMap.values()))) {
          Iterator<String> keyIterator = variables.iterator();
          Iterator<Double> valIterator = list.iterator();
          result.add(IntStream.range(0, list.size()).boxed()
              .collect(Collectors.toMap(i -> keyIterator.next(), i -> valIterator.next())));
        }
        break;
      default:
        throw new IllegalArgumentException("Unexpected value: " + gridOrList + " while building coordinates tuples");
    }
    return result;
  }
}


