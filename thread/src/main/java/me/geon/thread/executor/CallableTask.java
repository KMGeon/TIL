package me.geon.thread.executor;

import me.geon.thread.Utils;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<Integer> {

    private String name;
    private int sleepTime = 1000;

    public CallableTask(String name) {
        this.name = name;
    }

    public CallableTask(String name, int sleepTime) {
        this.name = name;
        this.sleepTime = sleepTime;
    }

    @Override
    public Integer call() throws Exception {
        Utils.logger(name + "실행");
        Utils.timeSleep(sleepTime);
        Utils.logger(name + "완료");
        return sleepTime;
    }
}
