package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static me.geon.thread.Utils.*;

public class FutureCancelMain {
    //private static boolean mayInterruptIfRunning = true; // 변경
    private static boolean mayInterruptIfRunning = false; // 변경

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
        logger("Future.state: " + future.state());

        // 일정 시간 후 취소 시도
        Utils.timeSleep(3000);

        // cancel() 호출
        logger("future.cancel(" + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future.cancel(false);
        logger("cancel(" + mayInterruptIfRunning + ") result: " + cancelResult);

        // 결과 확인
        try {
            logger("Future result: " + future.get());
        } catch (CancellationException e) {
            logger("Future는 이미 취소 되었습니다.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        es.close();
    }

    static class MyTask implements Callable<String> {
        @Override
        public String call() {
            try {
                for (int i = 0; i < 10; i++) {
                    logger("작업 중: " + i);
                    sleep(1000); // 1초 동안 sleep
                }
            } catch (InterruptedException e) {
                logger("인터럽트 발생");
                return "Interrupted";
            }
            return "Completed";
        }

    }
}
