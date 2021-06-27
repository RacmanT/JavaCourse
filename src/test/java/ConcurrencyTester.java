import org.junit.jupiter.api.Test;
import server.Server;

import java.io.*;


public class ConcurrencyTester {

  Server server = new Server(10000);
  private static final int NUMBER_OF_CLIENTS = 5;

  @Test
  public void test() throws InterruptedException {

    new Thread(() -> server.run()).start();
    new File("src/test/resources/results.txt").delete();
    String[] requests = {"STAT_REQS", "STAT_AVG_TIME", "MAX_GRID;x0:-1:0.1:1,x1:-10:1:20;((x0+(2.0^x1))/(21.1-x0));(x1*x0)", "STAT_MAX_TIME"};
    for (String request : requests) {
      for (Client client : initClients(request, NUMBER_OF_CLIENTS)) {
        client.start();
        Thread.sleep(300);
      }
    }
  }

  private Client[] initClients(String request, int number) {
    Client[] clients = new Client[number];
    for (int i = 0; i < number; i++) {
      clients[i] = new Client(request);
    }
    return clients;
  }



}
