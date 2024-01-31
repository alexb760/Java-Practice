package org.practice.concurrency.locks;

/**
 *
 * @author Alexander Bravo
 */
public class SyncCounter {
    private int count;

    /**
     * The synchronized key word can be in the method definition or inside the the method
     * when we put inside the method this allow as to do some other stuff like define which part
     * of the method will be Sync
     */
    public synchronized void increment(){
        count++;
    }

    public int getCount(){
        return count;
    }
}
