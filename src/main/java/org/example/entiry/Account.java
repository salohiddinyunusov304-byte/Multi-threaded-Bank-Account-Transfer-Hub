package org.example.entiry;

public class Account {
    String id;
    String ownerName;
    Double balance;

    public Account(String id, String ownerName, Double balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (balance < amount) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        balance += amount;
        return true;
    }
}
