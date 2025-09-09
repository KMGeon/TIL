package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RunnableMainV1 {
    public static void main(String[] args) throws InterruptedException {
        // 결과를 저장할 공유 변수
        AtomicInteger result = new AtomicInteger();
        
        // Thread 직접 생성하여 Runnable 실행
        Thread thread = new Thread(new MyRunnable(result));
        thread.start();
        
        // join()으로 스레드 완료 대기
        thread.join();
        
        Utils.logger("result value : " + result.get());
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