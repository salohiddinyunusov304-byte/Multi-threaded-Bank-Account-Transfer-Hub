package org.example.entiry;

import java.util.concurrent.Callable;

public class TransferTask implements Callable<String> {
    private final ProcessTransfer processTransfer;
    private final String taskId;
    private final String fromId;
    private final String toId;
    private final double amount;

    public TransferTask(ProcessTransfer processTransfer, String taskId, String fromId, String toId, double amount) {
        this.processTransfer = processTransfer;
        this.taskId = taskId;
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(500);

        boolean success = processTransfer.transfer("1", "2", 1000);

        if (success) {
            return "✅ O'tkazma muvaffaqiyatli: [" + 1 + "]" + " dan :: " + "[" + 2 + "] ga" + " -> " + 1000;
        }

        return "❌ Xatolik: Mablag' yetarli emas";
    }
}
