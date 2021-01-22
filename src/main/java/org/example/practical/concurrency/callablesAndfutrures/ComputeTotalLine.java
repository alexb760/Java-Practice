package org.example.practical.concurrency.callablesAndfutrures;

import static java.util.stream.Collectors.toList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * This class is just a simple example how we can implement a count lines recursively from a
 *  given directory by using {@link Future} and {@link Callable}
 *
 *  It can be done with simple sequential manner but... I want to think it should have better
 *  performance in huge directories containing large file.
 *
 * @author Alexander Bravo
 */
public class ComputeTotalLine {
  private final  Path dir = Paths.get("C:", "Laboral", "poll-catch");

  /**
   * Here just extract the {@link Stream} from the List and map to long by using the method
   * {@link ComputeTotalLine#extractValueFromFuture(Future)} finally sum them up.
   *
   * @return total of line present in the selected directory
   */
  public long computeTotalNumberOfLine() {
    long total = 0;
    try {
      total = executeCounters().stream().mapToLong(this::extractValueFromFuture).sum();
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
    return total;
  }

  /**
   * Interesting part: we created a {@link ExecutorService} and also call one of its many factories
   * methods which is {@link Executors#newCachedThreadPool()} this will give the capability of use and
   * reuse all available threads in the JVM, we could use come fixed thread methods but for simplicity
   * I decided go in this manner.
   * then it will invoke all the {@link Future} from our {@link ComputeTotalLine#getFileLineCounters()}
   *
   *
   * @return list of {@link Future}
   * @throws IOException more checked exception
   * @throws InterruptedException yet more checked exception
   */
  private List<Future<Long>> executeCounters() throws IOException, InterruptedException {
    ExecutorService service = Executors.newCachedThreadPool();
    List<Future<Long>> futures = service.invokeAll(getFileLineCounters());
    service.shutdown();
    return futures;
  }

  /**
   * Creates a List of {@link Callable} which will be executed later
   *
   * @return list of {@link Callable}
   * @throws IOException checked exception
   */
  private List<Callable<Long>> getFileLineCounters() throws IOException {
    return recursiveCall().map(this::callableCounterPrintingFile).collect(toList());
  }

  /**
   * It was planed to be recursive but neee. Java.nio has already a {@code walk()} method that
   * search depth first so I did
   *
   * <p>The extension filter can be improved also we can add some directory filter to skip some of
   * them that is the case in javaScript projects which have module directories it would have huge
   * amount of files and some of them large line not a good idea.
   *
   * @return {@link Stream} of {@link Path}
   * @throws IOException if something really bad happens
   */
  private Stream<Path> recursiveCall() throws IOException {
    return Files.walk(dir)
        // .filter(Files::isRegularFile)
        .filter(
            p ->
                p.toString().endsWith(".java")
                    || p.toString().endsWith(".sql")
                    || p.toString().endsWith(".csv"));
  }

  /**
   * Creates a task type {@link Callable} with a {@link Consumer} which will execute sometime in the
   * future the Line.count
   *
   * <p>Be careful: some file would have especial characters and makes it fails. some improve could
   * be a validation of the lines or something like that otherwise the program will end up throwing
   * an ugly exception from no where
   *
   * @param p a {@link Path} from where it is going to count the lines
   * @return the count of the lines
   */
  private Callable<Long> callableCounter(Path p) {
    return () -> Files.lines(p).count();
  }

  /**
   * Creates a task type {@link Callable} with a {@link Consumer} which will execute sometime in the
   * future the Line.count.
   *
   * <p>Be careful: some file would have especial characters and makes it fails. some improve could
   * be a validation of the lines or something like that.
   *
   * @param p a {@link Path} from where it is going to count the lines
   * @return the count of the lines
   */
  private Callable<Long> callableCounterPrintingFile(Path p) {

    return () -> {
      long count = Files.lines(p).count();
      System.out.printf("%s has %d line%n", p, count);
      return count;
    };
  }

  /**
   * takes a {@link Future} as argument a gets its value
   *
   * @param future {@link Future} type T
   * @return the value from the Future
   */
  private <T> T extractValueFromFuture(Future<T> future) {
    T value = null;
    try {
      value = future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return value;
  }

  public static void main(String[] args) {
    ComputeTotalLine compute = new ComputeTotalLine();
    System.out.println(compute.computeTotalNumberOfLine());
  }
}
