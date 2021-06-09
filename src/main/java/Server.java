import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.AtomicDouble;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Server {

/*
  private final Pattern computationPattern = Pattern.compile(Regex.COMPUTATION);
  private final Pattern statPattern = Pattern.compile(Regex.STAT);
  */

  private final int port;
  private final AtomicInteger okNumber = new AtomicInteger(0);
  private final AtomicDouble avgTime = new AtomicDouble(0);
  private final AtomicDouble maxTime = new AtomicDouble(0);
  private final Stopwatch timer = Stopwatch.createUnstarted();

  public Server(int port) {
    this.port = port;
  }

  public void run() throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        try {
          Socket socket = serverSocket.accept();
          new ClientHandler(socket, this).start();
        } catch (IOException e) {
          System.err.printf("Cannot accept connection due to %s", e);
        }
      }
    }
  }


  protected String buildResponse(String input) {
    try {
      timer.start();
      String answer = Request.parse(input, this).process();
      timer.stop();
      double time = Double.parseDouble(new DecimalFormat("#.000").format(timer.elapsed(MILLISECONDS)/1000.0));
      avgTime.set((avgTime.get() * okNumber.getAndIncrement() + time / okNumber.get()));
      if (time > maxTime.get()) {
        maxTime.set(time);
      }
      return "OK;" + time + ";" + answer;
    } catch (IOException | IllegalArgumentException e) {
      timer.stop();
      return "ERR;" + e.getMessage();
    }
  }

  public AtomicInteger getOkNumber() {
    return okNumber;
  }

  public AtomicDouble getAvgTime() {
    return avgTime;
  }

  public AtomicDouble getMaxTime() {
    return maxTime;
  }
}







