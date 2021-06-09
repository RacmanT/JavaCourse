
import util.Regex;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
  private final Socket socket;
  private final Server server;

  public ClientHandler(Socket socket, Server server) {
    this.socket = socket;
    this.server = server;
  }

  public void run() {

    try (socket) {
      BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      while (true) {
        String request = br.readLine();
        if (request == null) {
          System.err.println("Client abruptly closed connection");
          break;
        }
        if (request.matches(Regex.QUIT)) {
          break;
        }
        bw.write(server.buildResponse(request) + System.lineSeparator());
        bw.flush();
      }
    } catch (IOException e) {
      System.err.printf("IO error: %s", e);
    }
  }

}







