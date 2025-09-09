package me.geon.thread;

import static me.geon.thread.Utils.*;

import java.util.concurrent.locks.LockSupport;

public class Locksupport2 {
	public static void main(String[] args) {
		Thread t1 = new Thread(new ParkTest(), "Thread-1");
		t1.start();

		// 잠시 대기하여 T1이 park 상태에 빠질 시간을 준다.
		Utils.timeSleep(100);
		logger("T1 state: "+ t1.getState());

		logger("main -> unPark(t1)");
		// LockSupport.unpark(t1);
		// t1.interrupt();
	}

	static class ParkTest implements Runnable {
		@Override
		public void run() {
			logger("park 시작");
			LockSupport.park();
			logger("part 종료 , state : " + Thread.currentThread().getState());
			logger("part 종료 , 인터럽트 : " + Thread.currentThread().isInterrupted());
		}
	}
}
