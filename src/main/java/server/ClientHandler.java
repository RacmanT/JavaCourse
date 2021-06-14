package server;

import util.Regex;
import util.responses.Response;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
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

      System.out.print("[" + new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm:ss a").format(new Date()) + "] ");
      System.out.println("New connection from " + socket.getRemoteSocketAddress());

      while (true) {

        String input = br.readLine();
        if (input != null && input.matches(Regex.QUIT)) {
          System.out.print("[" + new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm:ss a").format(new Date()) + "] ");
          System.out.println("Client " + socket.getRemoteSocketAddress() + " disconnected");
          break;
        }

        bw.write(server.getExecutor().submit(() -> Response.build(input, server)).get() + System.lineSeparator());
        bw.flush();
      }
    } catch (IOException | InterruptedException | ExecutionException e) {
      System.err.printf("IO error: %s", e);
    }
  }

}







