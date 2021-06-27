package util.responses;

  /**
   * @author Tibor Racman
   * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class modeling an error response
   */

public class ErrorResponse extends Response {
  private static final String PREFIX = "ERR";

  public ErrorResponse(String result) {
    super(result);
  }

  @Override
  public String toString() {
    return PREFIX + ";" + result;
  }
}
