package org.practice.concurrency.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Bravo
 */
public class LockedCounter {
    private int count;
    private ReentrantLock lock = new ReentrantLock();

    public void increment(){
        lock.lock();
        try{
            count++;
        }finally{
            lock.unlock();
        }
    }
    public int getCount(){
        lock.lock();
        try{
            return count;
        }finally{
            lock.unlock();
        }
    }
}
