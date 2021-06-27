package util;

  /**
   * @author Tibor Racman
   * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class containing the regex used to parse the requests
   */

public abstract class Regex {

  public static final String STAT = "STAT_(REQS|AVG_TIME|MAX_TIME)";
  public static final String QUIT = "BYE";
  private static final String COMPUTATION_AND_VALUES_KIND = "(^(MIN|MAX|AVG|COUNT)_(GRID|LIST))";
  private static final String VARIABLE_VALUES_FUNCTION = "(?<variableValuesFunction>(([a-z]([0-9]+)?)(:-?[0-9]+(\\.[0-9]+)?){3}))(,(?'variableValuesFunction'))*";
  private static final String EXPRESSION = "(?<expression>([a-z](([0-9])+)?|[0-9]+(\\.[0-9]+)?|\\((?'expression')[\\+,\\-,\\*,\\/,\\^]{1}(?'expression')\\)))(;(?'expression'))*";
  public static final String COMPUTATION = COMPUTATION_AND_VALUES_KIND + ";" + VARIABLE_VALUES_FUNCTION + ";" + EXPRESSION;

}
