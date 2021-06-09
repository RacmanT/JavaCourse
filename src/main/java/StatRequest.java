
public class StatRequest implements Request {

  private final Server server;
  private final String input;

  public StatRequest(String input, Server server){
    this.input = input;
    this.server = server;
  }

  public String process(){
    String result = "";
    switch (input) {
      case "STAT_REQS" -> result =  String.valueOf(server.getOkNumber());
      case "STAT_AVG_TIME" -> result = String.valueOf(server.getAvgTime());
      case "STAT_MAX_TIME" -> result =  String.valueOf(server.getMaxTime());
    }
    return result;
  }

}
