package com.ndnhuy.poollearn.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.core.task.TaskDecorator;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadPoolTest {

    @Test
    void foo() throws ExecutionException, InterruptedException {
        var nTasks = 100;
        var startGate = new CountDownLatch(1);
        var endGate = new CountDownLatch(nTasks);
        var es = Executors.newFixedThreadPool(10);
        es = Executors.newWorkStealingPool(4);
        var futures = new ArrayList<Future>();
        for (int i = 0; i < nTasks; i++) {
            CompletableFuture.runAsync(() -> {
                try {
                    startGate.await();
                    Thread.sleep(100);
                    throw new RuntimeException("something wrong");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, new DecoratorExecutor(es)).exceptionally((ex) -> {
                endGate.countDown();
                return null;
            }).thenAccept((r) -> endGate.countDown());
        }

        var start = System.currentTimeMillis();
        startGate.countDown();
        Thread.sleep(1000);
        endGate.await();
        var end = System.currentTimeMillis();
        System.out.printf("time: %dms", end - start);
        Thread.sleep(2000);
        threadDump();
    }

    @Test
    void foo2() throws InterruptedException, ExecutionException, TimeoutException {
        var nTasks = 3;
        var startGate = new CountDownLatch(1);
        var endGate = new CountDownLatch(nTasks);
        var es = Executors.newFixedThreadPool(10);
        es = Executors.newWorkStealingPool(4);
        var futures = new ArrayList<Future<Void>>();
        for (int i = 0; i < nTasks; i++) {
            var f = CompletableFuture.runAsync(() -> {
                try {
                    startGate.await();
                    Thread.sleep(100);
                    throw new RuntimeException("something wrong");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, es);
            futures.add(f);
        }

        var start = System.currentTimeMillis();
        startGate.countDown();

        for (var f : futures) {
            try {
                f.get(500, TimeUnit.MILLISECONDS);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        var end = System.currentTimeMillis();
        System.out.printf("time: %dms", end - start);
        Thread.sleep(2000);
//        threadDump();
    }

    private void threadDump() {
        System.out.println("======= THREAD DUMP ======");
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();

        var info = new ArrayList<String>();
        for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stackTraceElements = entry.getValue();

            info.add(String.format("Thread: %s, state: %s\n", thread.getName(), thread.getState()));

//            for (StackTraceElement element : stackTraceElements) {
//                System.out.println("\tat " + element);
//            }
        }
        info.stream().sorted().forEach(System.out::println);
    }

    static class DecoratorExecutor implements Executor {

        private ExecutorService delegate;

        DecoratorExecutor(ExecutorService executorService) {
            delegate = executorService;
        }

        @Override
        public void execute(Runnable command) {
            var task = (ForkJoinTask) command;
            System.out.println(task.getException());
            delegate.execute(() -> command.run());
            System.out.println(task.getException());
        }
    }
}
