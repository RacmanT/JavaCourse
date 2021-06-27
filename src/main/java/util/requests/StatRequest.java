package util.requests;

import server.Server;

/**
 * @author Tibor Racman
 * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class modeling a server status request
 */

public class StatRequest extends Request {
  private final Server server;

  public StatRequest(String input, Server server) {
    super(input);
    this.server = server;
  }

  public String process() {
    String result = null;
    switch (input) {
      case "STAT_REQS" -> result = String.valueOf(server.getOkNumber());
      case "STAT_AVG_TIME" -> result = String.valueOf(server.getAvgTime());
      case "STAT_MAX_TIME" -> result = String.valueOf(server.getMaxTime());
    }
    return result;
  }

}
