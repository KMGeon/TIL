package me.geon.thread;

public class Join {
	public static void main(String[] args) {
		Utils.logger("Main Start ====");

		Job job1 = new Job(1, 50);
		Job job2 = new Job(51, 100);

		Thread t1 = new Thread(job1);
		Thread t2 = new Thread(job2);

		t1.start();
		t2.start();

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
			Utils.timeSleep(15_000);
			Utils.logger("작업끝 result = "+ rtn);
		}
	}
}
