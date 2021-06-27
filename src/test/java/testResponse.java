import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import server.Server;
import util.Logger;
import util.responses.Response;

/**
 * @author Tibor Racman
 * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class used to test the computation on the examples from the project page
 */

public class testResponse {

/*  @ParameterizedTest
  @ValueSource(strings = {"MAX_GRID;x0:-1:0.1:1,x1:-10:1:20;((x0+(2.0^x1))/(21.1-x0));(x1*x0)",
      "COUNT_LIST;x0:1:0.001:100;x1",
      "STAT_MAX_TIME"})
  public void testingValidResponses(String input) {
    System.out.println(input);
    Response response = Response.build(input, new Server(10000));
    Assertions.assertEquals("OK", response.toString().split(";")[0]);
  }*/

  @ParameterizedTest
  @ValueSource(strings = {"MAX_GRID;x0:-1:0.1:1,x1:-10:1:20;((x0+(2.0^x1))/(21.1-x0));(x1*x0)",
      "COUNT_LIST;x0:1:0.001:100;x1"})
  public void testingValidResponses(String input) {
    Response response = Response.build(input, new Server(10000));
    String[] tokens = response.toString().split(";");
    double result = Double.parseDouble(tokens[2]);
    Assertions.assertTrue(52150 <= result && result <= 52180 || 99000 <= result && result <= 99010);
    Logger.log(input + " gives " + result, false);
  }

  @ParameterizedTest
  @ValueSource(strings = {"AVG_LIST;x:1:1:100,y:0.1:0.1:10;((x+y)*x)",
  "AVG_GRID;x:1:1:100,y:0.1:0.1:10;((x+y)*x)"})
  public void testingValues(String input) {
    Response response = Response.build(input, new Server(10000));
    String[] tokens = response.toString().split(";");
    double result = Double.parseDouble(tokens[2]);
    Assertions.assertTrue(3500 <= result && result <= 3800);
    Logger.log(input + " gives " + result, false);
  }

  @ParameterizedTest
  @ValueSource(strings = {"MIN_LIST;x:1:1:100,y:0.1:0.1:10;((x+y)*(y-1))"})
  public void testingValues2(String input) {
    Response response = Response.build(input, new Server(10000));
    String[] tokens = response.toString().split(";");
    double result = Double.parseDouble(tokens[2]);
    Assertions.assertTrue(result < 0);
    Logger.log(input + " gives " + result, false);
  }

}
