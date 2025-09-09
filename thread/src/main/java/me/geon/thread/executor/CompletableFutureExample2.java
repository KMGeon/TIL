package me.geon.thread.executor;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample2 {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        });

        future.get();
        System.out.println("Thread: " + Thread.currentThread().getName());


        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();
        });

        System.out.println("Result : " + future2.get());
    }
}
