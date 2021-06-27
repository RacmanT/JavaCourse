package server;

import util.Logger;
import util.Regex;
import util.responses.Response;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

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

      Logger.log("New connection from " + socket.getRemoteSocketAddress(), false);

      while (true) {

        String input = br.readLine();
        if (input != null && input.matches(Regex.QUIT)) {
          Logger.log("Client " + socket.getRemoteSocketAddress() + " disconnected", false);
          break;
        }
        Logger.log("New request from " + socket.getRemoteSocketAddress(), false);
        bw.write(server.getExecutor().submit(() -> Response.build(input, server)).get() + System.lineSeparator());
        bw.flush();
      }
    } catch (IOException | InterruptedException | ExecutionException e) {
      Logger.log("IO error:" + e.getMessage(), true);
    }
  }

}







