package me.geon.thread.executor;

import me.geon.thread.Utils;

public class RunnableTask implements Runnable{

    private final String name;
    private int sleepMs = 1000;

    public RunnableTask(String name) {
        this.name = name;
    }

    public RunnableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public void run() {
        Utils.logger(name+"시작");
        Utils.timeSleep(sleepMs);
        Utils.logger(name+"완료");
    }
}
