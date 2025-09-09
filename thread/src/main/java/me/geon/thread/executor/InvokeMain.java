package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InvokeMain {
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask c1 = new CallableTask("task 1", 1000);
        CallableTask c2 = new CallableTask("task 3", 2000);
        CallableTask c3 = new CallableTask("task 3", 3000);

        List<CallableTask> tasks = List.of(c1, c2, c3);

        // 3가지 작업이 모두 끝나야지 return을 처리한다.
//        List<Future<Integer>> futures = es.invokeAll(tasks);
//        for (Future<Integer> future : futures) {
//            Integer value = future.get();
//            Utils.logger("value  = " + value);
//        }

        // 3가지 작업 중 하나의 작업만 처리되면 return하고 나머지는 인터럽트로 꺼버린다.
        Integer value = es.invokeAny(tasks);
        Utils.logger("value  = " + value);

    }
}
