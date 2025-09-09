package me.geon.thread.sync;

import static me.geon.thread.Utils.logger;
import static me.geon.thread.Utils.timeSleep;

public class BankMain {

    public static void main(String[] args) throws InterruptedException {
        //BankAccount account = new BankAccountV1(1000);
        //BankAccount account = new BankAccountV2(1000);
        //BankAccount account = new BankAccountV3(1000);
        //BankAccount account = new BankAccountV4(1000);
        //BankAccount account = new BankAccountV5(1000);
        BankAccount account = new BankAccountV6(1000);

        Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
        Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");

        t1.start();
        t2.start();

        timeSleep(500); // 검증 완료까지 잠시 대기
        logger("t1 state: " + t1.getState());
        logger("t2 state: " + t2.getState());

        t1.join();
        t2.join();

        logger("최종 잔액: " + account.getBalance());
    }
}
