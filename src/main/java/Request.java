import com.florianingerl.util.regex.Pattern;
import util.Regex;

import java.io.IOException;

public interface Request {

  Pattern computationPattern = Pattern.compile(Regex.COMPUTATION);
  Pattern statPattern = Pattern.compile(Regex.STAT);

  String process() throws IOException;

  static Request parse(String input, Server server) throws IOException {
    if (statPattern.matcher(input).matches()) {
      return new StatRequest(input, server);
    } else if (computationPattern.matcher(input).matches()) {
      return new ComputationRequest(input);
    } else {
      throw new IOException("Invalid request");
    }
  }
}
