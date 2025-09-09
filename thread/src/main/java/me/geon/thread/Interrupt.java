package me.geon.thread;

public class Interrupt {
	public static void main(String[] args) {
		MyTask task = new MyTask();
		new Thread(task, "workThread").start();

		Utils.timeSleep(4000);
		Utils.logger("작업중단 지시 returnFlag=False");
		task.runFlag = false;
		// - 바로 동작하지 않고 2초뒤에 실행된다.
		//23:40:48.543 [workThread] 작업 중
		// 23:40:51.550 [workThread] 작업 중
		// 23:40:52.531 [main] 작업중단 지시 returnFlag=False
		// 23:40:54.555 [workThread] 저원 정리
		// 23:40:54.556 [workThread] 저원 종료
	}

	static class MyTask implements Runnable {
		volatile boolean runFlag = true;
		@Override
		public void run() {
			while (runFlag) {
				Utils.logger("작업 중 ");
				Utils.timeSleep(3000);
			}
			Utils.logger("저원 정리");
			Utils.logger("저원 종료");
		}
	}
}
