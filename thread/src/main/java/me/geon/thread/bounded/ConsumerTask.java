package me.geon.thread.bounded;

import static me.geon.thread.Utils.logger;

public class ConsumerTask implements Runnable {

    private BoundedQueue queue;

    public ConsumerTask(BoundedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        logger("[소비 시도]     ? <- " + queue);
        String data = queue.take();
        logger("[소비 완료] " + data + " <- " + queue);
    }
}
