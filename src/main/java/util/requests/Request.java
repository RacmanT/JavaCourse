package util.requests;

import com.florianingerl.util.regex.Pattern;
import server.Server;
import util.Regex;

import java.util.concurrent.ExecutionException;

public abstract class Request {
  protected final String input;

  public Request(String input) {
    this.input = input;
  }

  public static Request parse(String input, Server server) throws IllegalArgumentException {
    if (Pattern.compile(Regex.STAT).matcher(input).matches()) {
      return new StatRequest(input, server);
    } else if (Pattern.compile(Regex.COMPUTATION).matcher(input).matches()) {
      return new ComputationRequest(input);
    } else if (Pattern.compile(Regex.QUIT).matcher(input).matches()) {
      return new QuitRequest();
    } else {
      throw new IllegalArgumentException("Invalid request");
    }
  }

  public abstract String process() throws IllegalArgumentException, ExecutionException;


}
