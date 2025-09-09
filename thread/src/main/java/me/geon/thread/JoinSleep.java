package me.geon.thread;

public class JoinSleep {
	public static void main(String[] args) throws InterruptedException {
		Utils.logger("Main Start ====");

		Job job1 = new Job(1, 50);
		Job job2 = new Job(51, 100);

		Thread t1 = new Thread(job1);
		Thread t2 = new Thread(job2);

		t1.start();
		t2.start();

		// join은 메인 스레드를 멈추고 t1이 끝날 때 기달린다.  -> Terminated 상태까지 대기
		Utils.logger("join() == 메인 스레드가 t1, t2 종료까지 대기");
		t1.join();
		t2.join();
		Utils.logger("join() == 스레드 대기 완료");

		Utils.logger("task1.result ==" + job1.rtn);
		Utils.logger("task2.result ==" + job2.rtn);

		int sumAll = job1.rtn + job2.rtn;

		Utils.logger("sumAll = " + sumAll);

		Utils.logger("Main End ====");
	}

	static class Job implements Runnable {
		int startV;
		int endV;
		int rtn = 0;

		public Job(int startV, int endV) {
			this.startV = startV;
			this.endV = endV;
		}

		@Override
		public void run() {
			Utils.logger("작업시작");

			int sum = 0;
			for(int i = startV; i <= endV; i++) {
				sum += i;
			}
			rtn += sum;
			Utils.timeSleep(2_000);
			Utils.logger("작업끝 result = "+ rtn);
		}
	}
}

/**
 * join : 호출 스레드는 대상 스레드가 완료될 때 까지 무한정 대기한다. -> TIMED
 * join(ms) : 특정 시간만큼 대기한다. 호출 스레드는 지정한 시간이 지나면 다시 runnable 샅태가 되면서 수행한다.
 *
 * -> thread에 join을 걸면 그 thread가 가장 우선순위가 높아져서 이게 다 끝나야지 다른 thread가 움직일 수 있게 도니다.
 */