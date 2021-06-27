package util.responses;

import server.Server;
import util.Logger;
import util.requests.Request;

import java.util.concurrent.ExecutionException;

/**
   * @author Tibor Racman
   * ADVANCED PROGRAMMING PROJECT 2020/21 - An abstract class modeling the concept of response
   */

public abstract class Response {
  protected final String result;

  protected Response(String result) {
    this.result = result;
  }

  public abstract String toString();

  public static Response build(String input, Server server) {
    try {
      double start = System.currentTimeMillis();
      String output = Request.parse(input, server).process();
      double finish = System.currentTimeMillis();
      double time = Math.pow(10, -3) * (finish - start);
      server.updateStatistics(time);
      return new OkResponse(time, output);
    } catch (IllegalArgumentException | ExecutionException e) {
      Logger.log("Error during computation because " + e.getMessage(), true);
      return new ErrorResponse("(" + e.getClass().getSimpleName() + ")" + " " + e.getMessage());
    }
  }

}
