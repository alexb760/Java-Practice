package org.practice.concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;

public abstract class HealthChecker implements Runnable {
    private final CountDownLatch latch;
    private final String serviceName;
    private boolean serviceUp;

    public HealthChecker(CountDownLatch latch, String serviceName){
        super();
        this.latch = latch;
        this.serviceName = serviceName;
    }

    @Override
    public void run() {
        try {
            checkerServiceAlive();
            //latch.countDown();
        }catch (Throwable t){
            serviceUp = false;
            t.printStackTrace();
        }finally {
            if (latch != null) latch.countDown();
        }

    }

    public String getServiceName(){
        return serviceName;
    }

    public abstract void checkerServiceAlive();
}
