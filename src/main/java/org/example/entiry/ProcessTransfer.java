package org.example.entiry;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessTransfer {
    private static final Lock lock = new ReentrantLock();
    private final AccountManager accountManager;

    public ProcessTransfer(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public boolean transfer(String fromId, String toId, double amount) {
        lock.lock();
        try {
            Account accountFrom = accountManager.findById(fromId);
            Account accountTo = accountManager.findById(toId);

            if (accountFrom == null || accountTo == null || accountFrom.getBalance() < amount) {
                return false;
            }
            boolean withdraw = accountFrom.withdraw(amount);
            boolean deposit = accountTo.deposit(amount);

            return withdraw && deposit;
        } finally {
            lock.unlock();
        }
    }
}
