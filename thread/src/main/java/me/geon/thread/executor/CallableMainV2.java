package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.Random;
import java.util.concurrent.*;

public class CallableMainV2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Utils.logger("submit() 호출");
        Future<Integer> future = es.submit(new MyCallable());
        Utils.logger("future 즉시 반환, future = " + future);
        
        Utils.logger("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 WAITING");
        Integer result = future.get();
        Utils.logger("future.get() [블로킹] 메서드 호출 완료 -> main 스레드 RUNNABLE");
        
        Utils.logger("result value = " + result);
        Utils.logger("future 완료, future = " + future);
        es.shutdown();
    }

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() {

            Utils.logger("Callable 시작");
            Utils.timeSleep(3000);
            int value = new Random().nextInt(10);
            Utils.logger("create Value = " + value);
            Utils.logger("Callable 끝");
            return value;
        }
    }
}
