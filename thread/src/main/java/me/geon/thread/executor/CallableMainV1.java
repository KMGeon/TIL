package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.Random;
import java.util.concurrent.*;

public class CallableMainV1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(new MyCallable());
        Integer rtn = future.get();
        Utils.logger("result value : " + rtn);
        es.close();
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
