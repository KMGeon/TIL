package me.geon.thread.executor;

import me.geon.thread.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CompletableFutureExample2Test {

    private ExecutorService es = Executors.newFixedThreadPool(10);

    @Test
    @DisplayName("""
            runAsync
            1. 반환값이 없는 경우
            2. 비동기로 작업 실행 콩
            """)
    public void runAsync() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        }, es);

        future.get();
        System.out.println("Thread: " + Thread.currentThread().getName());

        es.close();
    }


    @Test
    @DisplayName("""
            supplyAsync
            1. 반환값이 있는 경우
            2. 비동기로 작업 실행 콜
            """)
    public void supplyAsync() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return Thread.currentThread().getName();
        }, es);

        String rtn = future.get();
        Utils.logger("rtn: " + rtn);

        es.close();
    }

    @Test
    @DisplayName("""
            thenApply
            
            1. 반환 값을 받아서 다른 값을 반환함
            2. 함수형 인터페이스 Function을 파라미터로 받음
            """)
    public void thenApply() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();
        }, es).thenApply(s -> {
            return s.toUpperCase();
        });

        Utils.logger("rtn: " + future.get());
        es.close();
    }

    @Test
    @DisplayName("""
            thenAccpet
            
            반환 값을 받아 처리하고 값을 반환하지 않음
            함수형 인터페이스 Consumer를 파라미터로 받음
            """)
    void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();
        }, es).thenAccept(s -> {
            System.out.println(s.toUpperCase());
        });

        future.get();
        es.close();
    }

    @Test
    @DisplayName("""
            thenRun
            
            반환 값을 받지 않고 다른 작업을 실행함
            함수형 인터페이스 Runnable을 파라미터로 받음
            """)
    void thenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();
        }).thenRun(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        });

        future.get();
    }
}