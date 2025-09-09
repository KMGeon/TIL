package me.geon.thread.bounded;

import java.util.ArrayList;
import java.util.List;

import static me.geon.thread.Utils.logger;
import static me.geon.thread.Utils.timeSleep;

public class BoundedMain {

    public static void main(String[] args) {
        // 1. BoundedQueue 선택
        //BoundedQueue queue = new BoundedQueueV1(2);
        // BoundedQueue queue = new BoundedQueueV2(2);
//        BoundedQueue queue = new BoundedQueueV3(2);
//        BoundedQueue queue = new BoundedQueueV4(2);
        BoundedQueue queue = new BoundedQueueV5(2);
//        BoundedQueue queue = new BoundedQueueV6_1(2);
//        BoundedQueue queue = new BoundedQueueV6_2(2);
        //BoundedQueue queue = new BoundedQueueV6_3(2);

        // 2. 생산자, 소비자 실행 순서 선택, 반드시 하나만 선택!
        producerFirst(queue); // 생산자 먼저 실행
        //consumerFirst(queue); // 소비자 먼저 실행
    }

    private static void producerFirst(BoundedQueue queue) {
        logger("== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        logger("== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void consumerFirst(BoundedQueue queue) {
        logger("== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        logger("== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");

    }

    private static void startProducer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        logger("생산자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data" + i), "producer" + i);
            threads.add(producer);
            producer.start();
            timeSleep(100);
        }
    }

    private static void startConsumer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        logger("소비자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer" + i);
            threads.add(consumer);
            consumer.start();
            timeSleep(100);
        }
    }

    private static void printAllState(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        logger("현재 상태 출력, 큐 데이터: " + queue);
        for (Thread thread : threads) {
            logger(thread.getName() + ": " + thread.getState());
        }
    }


}
