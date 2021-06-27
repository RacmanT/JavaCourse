package util;

import java.text.SimpleDateFormat;
import java.util.Date;

  /**
   * @author Tibor Racman
   * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class modeling a simple Logger
   */

public class Logger {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm:ss a");

  public static void log(String string, boolean error) {
    if (error) {
      System.err.println("[" + SIMPLE_DATE_FORMAT.format(new Date()) + "] " + string);
    } else {
      System.out.println("[" + SIMPLE_DATE_FORMAT.format(new Date()) + "] " + string);
    }
  }


}
