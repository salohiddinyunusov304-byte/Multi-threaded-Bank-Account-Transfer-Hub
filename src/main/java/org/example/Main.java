package org.example;

import org.example.entiry.Account;
import org.example.entiry.AccountManager;
import org.example.entiry.ProcessTransfer;
import org.example.entiry.TransferTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        AccountManager accountManager = new AccountManager();

        // 1. Bir nechta Account yaratamiz va bankka qo'shamiz
        Account acc1 = new Account("ACC-101", "Anvar", 1000.0);
        Account acc2 = new Account("ACC-102", "Jasur", 500.0);
        Account acc3 = new Account("ACC-103", "Malika", 1500.0);
        Account acc4 = new Account("ACC-104", "Zuhra", 200.0);

        accountManager.addAccountNumber(acc1);
        accountManager.addAccountNumber(acc2);
        accountManager.addAccountNumber(acc3);
        accountManager.addAccountNumber(acc4);

        System.out.println("--- Boshlang'ich balanslar ---");
        accountManager.printAllBalances();

        // ProcessTransfer obyektini bitta umumiy accountManager bilan yaratamiz
        ProcessTransfer processTransfer = new ProcessTransfer(accountManager);

        // 2. Bir nechta parallel o'tkazma vazifalarini (TransferTask) tayyorlaymiz
        List<Callable<String>> tasks = new ArrayList<>();
        tasks.add(new TransferTask(processTransfer, "1", "ACC-101", "ACC-102", 300.0));
        tasks.add(new TransferTask(processTransfer, "2", "ACC-102", "ACC-103", 200.0));
        tasks.add(new TransferTask(processTransfer, "4", "ACC-104", "ACC-101", 500.0)); // Mablag' yetmaydi
        tasks.add(new TransferTask(processTransfer, "3", "ACC-103", "ACC-104", 400.0));
        tasks.add(new TransferTask(processTransfer, "5", "ACC-101", "ACC-103", 800.0));
        // 3. Thread Pool ochib, vazifalarni parallel ishga tushiramiz
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<>();

        System.out.println("\n--- O'tkazmalar boshlandi ---");
        for (Callable<String> task : tasks) {
            futures.add(executor.submit(task));
        }

        // 4. Natijalarni kutib olib ekranga chiqaramiz
        for (Future<String> future : futures) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Xatolik: " + e.getMessage());
            }
        }

        executor.shutdown();

        // 5. Yakuniy balanslarni tekshiramiz (Umumiy summa o'zgarmagan bo'lishi kerak: 3200$)
        System.out.println("\n--- Yakuniy balanslar ---");
        accountManager.printAllBalances();
    }
}