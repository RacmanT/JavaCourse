import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
  private final String request;

  public Client(String request){
    this.request = request;
  }

  public void run() {
    try (Socket socket = new Socket(InetAddress.getLocalHost(), 10000);
         BufferedWriter brFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/test/resources/results.txt", true)))){

      BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      bw.write(request + System.lineSeparator());
      bw.flush();

      brFile.write("Sent: " + request + " Received: " + br.readLine() + System.lineSeparator());
      brFile.flush();
      bw.write("BYE" + System.lineSeparator());
      bw.flush();
    } catch (IOException e) {
      e.getMessage();
    }
  }
}