package org.example.entiry;

import java.util.concurrent.ConcurrentHashMap;

public class AccountManager {
    ConcurrentHashMap<String, Account> accountNumber = new ConcurrentHashMap<>();

    public boolean addAccountNumber(Account account) {
        Account put = accountNumber.put(account.id, account);

        if (put != null) {
            return true;
        }
        return false;
    }

    public Account findById(String id) {
        return accountNumber.get(id);
    }

    public void printAllBalances() {
        double total = 0;
        for (Account acc : accountNumber.values()) {
            System.out.println(acc.getId() + " (" + acc.getOwnerName() + "): $" + acc.getBalance());
            total += acc.getBalance();
        }
        System.out.println("Bankdagi jami mablag': $" + total);
    }
}
