package org.practice.concurrency.locks;

/**
 * @author Alexander Bravo
 */
public class Counter {
    private int count;

    public void increment(){
        count++;
    }
    public int getCount(){
        return count;
    }
}
