package org.practice.concurrency.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class DatabaseChecker extends HealthChecker{

    private final int secretNumber = new Random().nextInt(5);
    private int threshold = 0;
    public DatabaseChecker(CountDownLatch latch) {
        super(latch, "Dabase Service");
    }

    @Override
    public void checkerServiceAlive() {
        int pingResponse;
        while (threshold <= 6){
           // try {
                System.out.println("Sending ping to " +  getServiceName());
                //Thread.sleep(1000);
                pingResponse = new Random().nextInt(5);
                if (pingResponse == secretNumber){
                    System.out.println("Service is Up: " + getServiceName());
                    break;
                }
                threshold ++;
            //} catch (InterruptedException e) {
               // throw new RuntimeException(e);
            //}
        }
    }
}
