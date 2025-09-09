package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RunnableMainV2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        
        // 결과를 저장할 공유 변수
        AtomicInteger result = new AtomicInteger();
        
        // submit()은 Future<?>를 반환
        Future<?> future = es.submit(new MyRunnable(result));
        
        // get()으로 작업 완료 대기 (Callable의 get()과 동일한 역할)
        future.get();
        
        Utils.logger("result value : " + result.get());
        es.shutdown();
    }

    static class MyRunnable implements Runnable {
        private final AtomicInteger result;
        
        public MyRunnable(AtomicInteger result) {
            this.result = result;
        }
        
        @Override
        public void run() {
            Utils.logger("Runnable 시작");
            Utils.timeSleep(3000);
            int value = new Random().nextInt(10);
            Utils.logger("create Value = " + value);
            result.set(value);  // 결과를 공유 변수에 저장
            Utils.logger("Runnable 끝");
        }
    }
}