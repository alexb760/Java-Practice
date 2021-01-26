package org.example.practical.concurrency.locks;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Bravo
 */
public class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment(){
        count.incrementAndGet();
    }
    public int getCount(){
        return count.get();
    }
}
