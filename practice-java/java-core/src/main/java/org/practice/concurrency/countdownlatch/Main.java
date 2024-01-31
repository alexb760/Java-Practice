package org.practice.concurrency.countdownlatch;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        List<HealthChecker> servers = List.of(new Cachechecker(latch), new DatabaseChecker(latch));

        ExecutorService executor = Executors.newFixedThreadPool(servers.size());
        //Executor executor = Executors.newFixedThreadPool(servers.size());
        for (final HealthChecker checker : servers){
            executor.execute(checker);
        }

        latch.await();
        executor.shutdown();
        System.out.println("all Services were tested.");


    }
}
