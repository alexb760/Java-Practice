package concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This es a quick example how we can solve the Consume Produce problem
 * using {@link BlockingQueue} and executed then in different threads.
 *
 * @author Alexander Bravo
 */
public class ProduceConsumeDemo {
  public static void main(String[] args) {
    BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    Producer p1 = new Producer(1, queue);
    Consumer c1 = new Consumer(1, queue);

    Producer p2 = new Producer(2, queue);
    Consumer c2 = new Consumer(2, queue);

    Producer p3 = new Producer(3, queue);
    Consumer c3 = new Consumer(3, queue);

    ExecutorService service = Executors.newCachedThreadPool();
    service.execute(p1);
    service.execute(c1);

    service.execute(p2);
    service.execute(c2);

    service.execute(p3);
    service.execute(c3);

    service.shutdown();
  }
}
