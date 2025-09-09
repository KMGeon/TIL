package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.concurrent.*;

/**
 * Future vs CompletableFuture 차이점 비교
 */
public class CompletableFutureExample {
    public static void main(String[] args) throws Exception {
        Utils.logger("=== 2개 스레드로 blocking vs non-blocking 비교 ===");

        ExecutorService es = Executors.newFixedThreadPool(10);

//         1. Future 방식 - 블로킹 (순차 처리)
        Utils.logger("1. Future 방식 - 2개 작업을 순차적으로 처리 (블로킹):");

        Future<String> future1 = es.submit(new MyWorker("Future-Worker-1"));
        Future<String> future2 = es.submit(new MyWorker("Future-Worker-2"));
        Utils.logger("2개 Future 작업 시작됨");

        // 블로킹으로 결과 받기 - 순차적으로 대기
        Utils.logger("future1.get() 호출 - 첫 번째 작업 완료까지 블로킹");
        String result1 = future1.get(); // 블로킹
        Utils.logger("future1 완료: " + result1);

        Utils.logger("future2.get() 호출 - 두 번째 작업 완료까지 블로킹");
        String result2 = future2.get(); // 블로킹
        Utils.logger("future2 완료: " + result2);
        Utils.logger("===== Future 방식 완료 =====\n");

        //> Task :me.geon.thread.executor.CompletableFutureExample.main()
        //05:44:32.664 [main] === 2개 스레드로 blocking vs non-blocking 비교 ===
        //05:44:32.666 [main] 1. Future 방식 - 2개 작업을 순차적으로 처리 (블로킹):
        //05:44:32.667 [main] 2개 Future 작업 시작됨
        //05:44:32.667 [Future-Worker-1] Worker 시작
        //05:44:32.667 [main] future1.get() 호출 - 첫 번째 작업 완료까지 블로킹
        //05:44:32.667 [Future-Worker-2] Worker 시작
        //05:44:37.670 [Future-Worker-1] Worker 끝
        //05:44:37.670 [Future-Worker-2] Worker 끝
        //05:44:37.673 [main] future1 완료: Future-Worker-1
        //05:44:37.673 [main] future2.get() 호출 - 두 번째 작업 완료까지 블로킹
        //05:44:37.673 [main] future2 완료: Future-Worker-2
        //05:44:37.673 [main] ===== Future 방식 완료 =====

        // 2. CompletableFuture 방식 - 논블로킹 (동시 처리)
        Utils.logger("2. CompletableFuture 방식 - 2개 작업을 동시에 처리 (논블로킹):");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                return new MyWorker("CompletableFuture-Worker-1").call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                return new MyWorker("CompletableFuture-Worker-2").call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Utils.logger("2개 CompletableFuture 작업 시작됨");

        // 논블로킹 콜백 등록
        cf1.thenAccept(result -> Utils.logger("CompletableFuture-1 완료: " + result));
        cf2.thenAccept(result -> Utils.logger("CompletableFuture-2 완료: " + result));
        Utils.logger("콜백 등록 완료 - 논블로킹이라 즉시 진행");

        // 메인 스레드는 즉시 다른 작업 수행 가능
        Utils.logger("메인 스레드는 즉시 다른 작업 수행 가능");
        for (int i = 1; i <= 5; i++) {
            Utils.logger("메인 스레드 작업 " + i);
            Utils.timeSleep(1000);
        }
        Utils.logger("===== CompletableFuture 방식 완료 =====");

        //> Task :me.geon.thread.executor.CompletableFutureExample.main()
        //05:43:38.112 [main] === 2개 스레드로 blocking vs non-blocking 비교 ===
        //05:43:38.114 [main] 2. CompletableFuture 방식 - 2개 작업을 동시에 처리 (논블로킹):
        //05:43:38.116 [main] 2개 CompletableFuture 작업 시작됨
        //05:43:38.116 [CompletableFuture-Worker-1] Worker 시작
        //05:43:38.116 [CompletableFuture-Worker-2] Worker 시작
        //05:43:38.117 [main] 콜백 등록 완료 - 논블로킹이라 즉시 진행
        //05:43:38.117 [main] 메인 스레드는 즉시 다른 작업 수행 가능
        //05:43:38.119 [main] 메인 스레드 작업 1
        //05:43:39.125 [main] 메인 스레드 작업 2
        //05:43:40.131 [main] 메인 스레드 작업 3
        //05:43:41.131 [main] 메인 스레드 작업 4
        //05:43:42.136 [main] 메인 스레드 작업 5
        //05:43:43.120 [CompletableFuture-Worker-1] Worker 끝
        //05:43:43.121 [CompletableFuture-Worker-2] Worker 끝
        //05:43:43.123 [CompletableFuture-Worker-1] CompletableFuture-1 완료: CompletableFuture-Worker-1
        //05:43:43.123 [CompletableFuture-Worker-2] CompletableFuture-2 완료: CompletableFuture-Worker-2
        //05:43:43.141 [main] ===== CompletableFuture 방식 완료 =====

        // 완료 대기
        Thread.sleep(3000);

        es.shutdown();
    }

    static class MyWorker implements Callable<String> {
        private String threadName;

        public MyWorker(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public String call() throws Exception {
            Thread.currentThread().setName(threadName);
            Utils.logger("Worker 시작");
            Utils.timeSleep(5000);
            Utils.logger("Worker 끝");
            return threadName.toString();
        }
    }
}

/*
=== 핵심 차이점 요약 ===

1. **생성과 실행**
   - Future: ExecutorService에 의존, submit() 필요
   - CompletableFuture: 독립적 실행 가능, supplyAsync() 등 사용

2. **결과 처리**
   - Future: get()으로만 블로킹 방식 결과 획득
   - CompletableFuture: get() + 콜백(thenAccept, thenApply 등) 논블로킹 처리

3. **체이닝**
   - Future: 불가능, 각 단계마다 get() 호출 필요
   - CompletableFuture: 강력한 체이닝 지원 (thenApply, thenCompose 등)

4. **조합**
   - Future: 수동으로 각각 get() 후 결합
   - CompletableFuture: thenCombine, allOf, anyOf 등 자동 조합

5. **예외 처리**
   - Future: try-catch로만 처리, ExecutionException 래핑
   - CompletableFuture: exceptionally, handle 등으로 체이닝 내에서 처리

6. **완료 확인**
   - Future: isDone(), cancel() 정도만 지원
   - CompletableFuture: complete(), completeExceptionally() 등 더 많은 제어

결론: CompletableFuture는 Future의 모든 기능을 포함하면서도
      함수형 프로그래밍과 논블로킹 처리를 지원하는 훨씬 강력한 도구
*/
