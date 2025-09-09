package me.geon.thread.executor;

import java.util.concurrent.ExecutorService;

public abstract class ExecutorUtils {
    public static void printState(ExecutorService executor) {
        if (executor instanceof java.util.concurrent.ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queuedTasks = poolExecutor.getQueue().size();
            long completedTask = poolExecutor.getCompletedTaskCount();
            System.out.println("pool=" + pool + ", active=" + active + ", queuedTasks=" + queuedTasks + ", completedTask=" + completedTask);
        } else {
            System.out.println(executor);
        }
    }
}
