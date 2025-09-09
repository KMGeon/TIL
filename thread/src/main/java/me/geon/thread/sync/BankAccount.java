package me.geon.thread.sync;

public interface BankAccount {

    boolean withdraw(int amount);

    int getBalance();
}
