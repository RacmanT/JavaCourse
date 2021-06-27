import server.Server;
import util.Logger;

  /**
   * @author Tibor Racman
   * ADVANCED PROGRAMMING PROJECT 2020/21 - The Main Class
   */

public class Main {

  public static void main(String[] args) {

    try {

      Server server = new Server(/*Integer.parseInt(args[0])*/10000);
      server.run();
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
      Logger.log("Port parameter does not contain a parsable integer", true);
    }

  }
}


/*    AVG_LIST;x:1:1:100,y:0.1:0.1:10;((x+y)*x)
    REF:	OK;0.007;3721.850000
    TEST:	OK;0,602;6767.0
    AVG_GRID;x:1:1:100,y:0.1:0.1:10;((x+y)*x)
    REF:	OK;0.034;3638.525000
    TEST:	OK;0,969;6767.0
    MIN_LIST;x:1:1:100,y:0.1:0.1:10;((x+y)*(y-1))
    REF:	OK;0.012;-2.750000
    TEST:	OK;0,978;0.0
    IO error:java.lang.NullPointerException: Cannot invoke "java.lang.CharSequence.length()" because "this.text" is null*/



