package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.concurrent;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ReadWriteTaskExecutor {
    public static void main(String[] args) throws InterruptedException {
        TabulatedFunction function = new LinkedListTabulatedFunction(new ZeroFunction(), 1, 10, 10);
        List<Thread> list = new ArrayList<>();
        int countThread = 20;
        CountDownLatch countDownLatch = new CountDownLatch(countThread);
        ReadWriteTask myTask = new ReadWriteTask(function, countDownLatch::countDown);
        for (int i = 0; i < countThread; i++) {
            list.add(new Thread(myTask));
        }

        for (Thread thread : list) {
            thread.start();
        }
        countDownLatch.await();
        System.out.println(function.toString());

    }
}
