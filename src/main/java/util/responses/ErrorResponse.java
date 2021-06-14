package util.responses;

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
