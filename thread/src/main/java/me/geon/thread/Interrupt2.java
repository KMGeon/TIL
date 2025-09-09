package me.geon.thread;

import static me.geon.thread.Utils.*;

public class Interrupt2 {
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Thread thread = new Thread(task, "work");
		thread.start();

		timeSleep(4000);
		logger("작업 중단 지시 thread.interrupt()");
		thread.interrupt();
		logger("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());
	}

	static class MyTask implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					logger("작업 중");
					Thread.sleep(3000);
				}
			} catch (InterruptedException e) {
				logger("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
				logger("interrupt message=" + e.getMessage());
				logger("state=" + Thread.currentThread().getState());
			}
			logger("자원 정리");
			logger("자원 종료");
		}
	}
}
