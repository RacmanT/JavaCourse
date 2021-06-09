import com.google.common.collect.Lists;
import expression.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {

  private static final int PORT = 10000;
  //TODO: pass PORT through command line, rename FinalProject in finalProject (name convention!)

  public static void main(String[] args) throws IOException {

    List<String> expressions = new ArrayList<>(2);
    expressions.add("((x0+(2.0^x1))/(21.1-x0))");
    expressions.add("(x1*x0)");

    List<List<Double>> lists = new ArrayList<>();
    lists.add(buildList(-1,0.1,1));
    lists.add(buildList(-10,1,20));
    List<List<Double>> listOfValues = Lists.cartesianProduct(lists);

    List<Double> lista = DoubleStream.iterate(1, k ->  k <= 10, k -> 1+k*1).boxed().collect(Collectors.toList());
    System.out.println(lista);


/*
    int cores = Runtime.getRuntime().availableProcessors();
    Server server = new Server(PORT);
    server.run();
*/

  }

  private static ArrayList<Double> buildList(double lower, double step, double upper) {
    ArrayList<Double> tmp = new ArrayList<>();
    IntStream.rangeClosed(0, Double.valueOf((upper - lower) / step).intValue()).forEach(k -> tmp.add(lower + k * step));
    //  IntStream.rangeClosed(0, (int)((upper - lower) / step)).forEach(k -> tmp.add(lower + k * step));
    return tmp;

/*      for (int k = 0; lower + k * step <= upper; k++) {
        tmp.add(lower + k * step);
      }
      return tmp;
      */
  }

}

