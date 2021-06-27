package server;

import com.google.common.util.concurrent.AtomicDouble;
import util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Server {
  private final int port;
  private final AtomicInteger okNumber;
  private final AtomicDouble avgTime;
  private final AtomicDouble maxTime;
  private final ExecutorService executor;

  public Server(int port) {
    this.port = port;
    okNumber = new AtomicInteger(0);
    avgTime = new AtomicDouble(0);
    maxTime = new AtomicDouble(0);
    executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  }

  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      Logger.log("Server is up and is listening on port " + port, false);
      while (true) {
        try {
          new ClientHandler(serverSocket.accept(), this).start();
        } catch (IOException e) {
          Logger.log("Cannot accept connection due to " + e.getMessage(), true);
        }
      }
    } catch (IOException e) {
      Logger.log("Cannot create Socket because " + e.getMessage(), true);
    } finally {
      executor.shutdown();
    }
  }

  public void updateStatistics(double time) {
    avgTime.set((avgTime.get() * okNumber.getAndIncrement() + time) / okNumber.get());
    if (time > maxTime.get()) {
      maxTime.set(time);
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

  public ExecutorService getExecutor() {
    return executor;
  }
}







