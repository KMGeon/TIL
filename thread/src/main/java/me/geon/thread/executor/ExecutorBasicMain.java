package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorBasicMain {
    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(
                2,
                2,
                0,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>()
        );

        Utils.logger("초기상태");
        ExecutorUtils.printState(es);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("taskD"));
        Utils.logger("==작업 수행 중 ==");
        ExecutorUtils.printState(es);

        // 초기에 작업이 들어와야지 만든다. 이후에 만든 거를 재사용 한다.
        Utils.timeSleep(3000);
        Utils.logger("=== 작업 수행 완료 ==");
        ExecutorUtils.printState(es);


        // 자바 19 이상에서 가능하다.
        es.close(); // 새로운 작업 받지 않고 기존 작업 완료 대기
        ExecutorUtils.printState(es);
    }
}
