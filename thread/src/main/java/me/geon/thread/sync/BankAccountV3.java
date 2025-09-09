package me.geon.thread.sync;

import static me.geon.thread.Utils.logger;
import static me.geon.thread.Utils.timeSleep;

public class BankAccountV3 implements BankAccount {

    private int balance;

    public BankAccountV3(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean withdraw(int amount) {
        logger("거래 시작: " + getClass().getSimpleName());

        synchronized (this) {
            logger("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                logger("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                return false;
            }

            // 잔고가 출금액 보다 많으면, 진행
            logger("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            timeSleep(1000); // 출금에 걸리는 시간으로 가정
            balance = balance - amount;
            logger("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
        }

        logger("거래 종료");
        return true;
    }

    @Override
    public synchronized int getBalance() {
        return balance;
    }
}
