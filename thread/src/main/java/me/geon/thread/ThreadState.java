package me.geon.thread;

import static me.geon.thread.Utils.*;

/**
 * 쓰레드 생명주기
 */
public class ThreadState {
	public static void main(String[] args) {
		System.out.println("======================================");
		Thread thread = new Thread(new StateRunnable(), "stateThread");
		logger("state : " + thread.getState());
		thread.start();
		// logger("state : " + thread.getState());
	}

	static class StateRunnable implements Runnable {
		@Override
		public void run() {
			logger("start : " + Thread.currentThread().getState());
		}
	}
}
