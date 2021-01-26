package org.example.practical.concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;

/** @author Alexander Bravo */
public class Producer implements Runnable {
  private final int offset = 100;
  private int id;
  private BlockingQueue<Message> queue;

  public Producer(int id, BlockingQueue<Message> queue) {
    this.id = id;
    this.queue = queue;
  }

  @Override
  public void run() {
    for (int i = 0; i < offset; i++) {
      try {
        Message msg = new Message(i);
        System.out.printf("Producer %d produced %d%n", id, msg.getId());
        queue.put(msg);
        Thread.sleep((long) (Math.random() * 100));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    // here  indicate we have finished
    try{
        queue.put(new Message(-1));
    }catch (InterruptedException e){
        e.printStackTrace();
    }
  }
}
