package me.geon.thread;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Utils {

	private static final DateTimeFormatter formatter =
		DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

	public static void logger(Object obj) {
		String time = LocalTime.now().format(formatter);
		System.out.printf("%s [%s] %s\n", time, Thread.currentThread().getName(), obj);
	}

	public static void timeSleep(long millis) {
			try {
				Thread.sleep(millis);
			}catch (InterruptedException e){
				logger("인터럽트 발생, "+ e.getMessage());
				throw new RuntimeException(e);
			}
	}
}
