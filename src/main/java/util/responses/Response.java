package util.responses;

import server.Server;
import util.requests.Request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public abstract class Response {
  protected final String result;

  protected Response(String result) {
    this.result = result;
  }

  public static Response build(String input, Server server) {

    try {
      server.getTimer().start();
      String output = Request.parse(input, server).process();
      double time = server.getTimer().elapsed(MILLISECONDS) / 1000.0;
      server.updateStatistics(time);
      return new OkResponse(time, output);
    } catch (IllegalArgumentException | ExecutionException e) {
      System.err.print("[" + new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm:ss a").format(new Date()) + "] ");
      System.err.println("Error during computation because " + e.getMessage());
      return new ErrorResponse("(" + e.getClass().getSimpleName() + ")" + " " + e.getMessage());
    } finally {
      server.getTimer().stop();
    }
  }

  public abstract String toString();


}
