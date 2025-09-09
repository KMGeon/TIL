package me.geon.thread;

import static me.geon.thread.Utils.*;

public class ThreadInfo {
	public static void main(String[] args) {
		Thread mainThread = Thread.currentThread();
		logger("mainThread = " + mainThread);
		logger("mainThread Id = " + mainThread.getId());
		logger("mainThreadName = " + mainThread.getName());
		logger("mainThread.getPriority() = " + mainThread.getPriority());
		logger("mainThread.getThreadGroup() = " + mainThread.getThreadGroup());
		logger("mainThread.getState() = " + mainThread.getState());

		// > Task :me.geon.thread.ThreadInfo.main()
		// 18:20:17.079 [main] mainThread = Thread[#1,main,5,main]
		// 18:20:17.083 [main] mainThread Id = 1 # 중복되지 않는다.
		// 18:20:17.083 [main] mainThreadName = main
		// 18:20:17.084 [main] mainThread.getPriority() = 5 # 우선순위  : 기본이 5 (default) 우선순위가 높을수록 많이 수행한다. (OS에서 우선순위를 알려준다.)
 		// 18:20:17.085 [main] mainThread.getThreadGroup() = java.lang.ThreadGroup[name=main,maxpri=10]
		// 18:20:17.085 [main] mainThread.getState() = RUNNABLE

		System.out.println("======================");

		Thread thread1 = new Thread(new HelloRunnable(), "myThread1");
		logger("mainThread = " + thread1);
		logger("mainThread Id = " + thread1.getId());
		logger("mainThreadName = " + thread1.getName());
		logger("mainThread.getPriority() = " + thread1.getPriority());
		logger("mainThread.getThreadGroup() = " + thread1.getThreadGroup());
		logger("mainThread.getState() = " + thread1.getState());

		thread1.start();

		logger("mainThread.getState() = " + thread1.getState()); // runnable

		// 18:24:11.485 [main] mainThread = Thread[#20,myThread1,5,main]
		// 18:24:11.485 [main] mainThread Id = 20
		// 18:24:11.485 [main] mainThreadName = myThread1
		// 18:24:11.485 [main] mainThread.getPriority() = 5
		// 18:24:11.485 [main] mainThread.getThreadGroup() = java.lang.ThreadGroup[name=main,maxpri=10]
		// 18:24:11.486 [main] mainThread.getState() = NEW # 아직 시작을 안해서 new임
	}

	static class HelloRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + ": run()");
		}
	}
}

/**
 * ==================== Thread 정보 정리 ====================
 * 
 * 1. ThreadGroup
 *    - 스레드가 속한 그룹을 반환한다.
 *    - 기본적으로 모든 스레드는 부모 스레드와 동일한 스레드 그룹에 속한다.
 *    - 스레드 그룹을 기준으로 일괄 종료, 우선순위 설정을 할 수 있다.
 *    - 부모 스레드: 위 코드를 예시로 myThread1의 부모는 main 스레드이다.
 * 
 * 2. Thread State (getState() 메서드)
 *    - 스레드의 현재 상태를 반환하는 메서드
 *    - 반환되는 값은 Thread.State 열거형에 정의된 상수 중 하나
 * 
 *    주요 상태:
 *    ┌─────────────────┬────────────────────────────────────────────────┐
 *    │ NEW             │ 스레드가 아직 시작되지 않은 상태                  │
 *    │ RUNNABLE        │ 스레드가 실행 중이거나 실행될 준비가 된 상태       │
 *    │ BLOCKED         │ 스레드가 동기화 락을 기다리는 상태                │
 *    │ WAITING         │ 스레드가 다른 스레드의 특정 작업 완료를 기다리는 상태 │
 *    │ TIMED_WAITING   │ 일정 시간 동안 기다리는 상태                     │
 *    │ TERMINATED      │ 스레드가 실행을 마친 상태                        │
 *    └─────────────────┴────────────────────────────────────────────────┘
 */