package me.geon.thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static me.geon.thread.Utils.logger;
import static me.geon.thread.Utils.timeSleep;

public class BoundedQueueV2 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV2(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            logger("[put] 큐가 가득 참, 생산자 대기");
            timeSleep(1000);
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            logger("[take] 큐에 데이터가 없음, 소비자 대기");
            timeSleep(1000);
        }
        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
