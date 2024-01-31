package org.practice.concurrency.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

/**
 * This are a simple example or technics to coordinate thread it comes handy when your process
 * requires run in different thread and then at the end you need to coordinate all.
 * we are going to see
 * <p>
 * <p>{@code synchronized}
 * <p>{@link Lock}
 * <p>{@link AtomicInteger}
 *<p>
 * the mentioned methods above  may not make your algorithm run faster if you want so you should see
 * some parallel technics like dive and conquer technic
 *
 * @author Alexander Bravo
 */
public class LocksDemo {

    final private int offset = 100_000_000;
    final private Counter counter = new Counter();
    final private SyncCounter syncCounter = new SyncCounter();
    final private LockedCounter lockedCounter = new LockedCounter();
    final private AtomicCounter atomicCounter = new AtomicCounter();

    /**
     * Since this version is not using any Lock thread will end up aver written each other
     * and never will print the real value at the end
     */
    public void demoCounter(){
        ExecutorService service = Executors.newCachedThreadPool();
        IntStream.range(0, offset)
            //summit does not return any value back
            .forEach(i -> service.submit(counter::increment));

        service.shutdown();
        System.out.println("Counter count =" + counter.getCount());
    }

    public void demoSyncCounter(){
        ExecutorService service = Executors.newCachedThreadPool();
        IntStream.range(0, offset)
            .forEach(i -> service.submit(syncCounter::increment));

        service.shutdown();
        System.out.println("Sync Counter count ="+ syncCounter.getCount());
    }

    public void demoLockCounter(){
        ExecutorService service = Executors.newCachedThreadPool();
        IntStream.range(0, offset)
            .forEach(i -> service.submit(lockedCounter::increment));

        service.shutdown();
        System.out.println("lock Counter count ="+ lockedCounter.getCount());
    }

    public void demoAtomicCounter(){
        ExecutorService service = Executors.newCachedThreadPool();
        IntStream.range(0, offset)
            .forEach(i -> service.submit(atomicCounter::increment));

        service.shutdown();
        System.out.println("Atomic Counter count ="+ atomicCounter.getCount());
    }

    /**
     * A micro benchmark this no represent a accurate result but will work in this case
     * @param m message
     * @param starTime start Time
     */
    public static void printTimeConsumed(String m, long starTime){
    System.out.printf(">> %s took %f5 sec\n", m, (System.nanoTime() - starTime) / 1E9);
    }

  public static void main(String[] args) {
        long startT = 0L;
    LocksDemo demo = new LocksDemo();
    //With aou locks
      startT = System.nanoTime();
     demo.demoCounter();
     printTimeConsumed("Secuential", startT);
    // Using Synchronized method
      startT = System.nanoTime();
    demo.demoSyncCounter();
      printTimeConsumed("Syncronized", startT);
    //Lock method
      startT = System.nanoTime();
     demo.demoLockCounter();
      printTimeConsumed("lock", startT);
     //Atomic method
      startT = System.nanoTime();
      demo.demoAtomicCounter();
      printTimeConsumed("Atomic", startT);
  }
}
