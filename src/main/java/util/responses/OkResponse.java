package util.responses;

  /**
   * @author Tibor Racman
   * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class modeling a succesfull response
   */

public class OkResponse extends Response {

  private static final String PREFIX = "OK";
  private final double time;

  public OkResponse(double time, String result) {
    super(result);
    this.time = time;
  }

  @Override
  public String toString() {
    return PREFIX + ";" + String.format("%.3f", time) + ";" + result;
  }
}
