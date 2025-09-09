package me.geon.thread.bounded;

import static me.geon.thread.Utils.logger;

public class ProducerTask implements Runnable {

    private BoundedQueue queue;
    private String request;

    public ProducerTask(BoundedQueue queue, String request) {
        this.queue = queue;
        this.request = request;
    }

    @Override
    public void run() {
        logger("[생산 시도] " + request + " -> " + queue);
        queue.put(request);
        logger("[생산 완료] " + request + " -> " + queue);
    }
}
