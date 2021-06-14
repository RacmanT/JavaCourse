import server.Server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

  //TODO: pass PORT through command line

  public static void main(String[] args) {

    try {

      Server server = new Server(Integer.parseInt(args[0]));
      server.run();
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
      System.err.println("Port parameter does not contain a parsable integer");
    }

  }
}

