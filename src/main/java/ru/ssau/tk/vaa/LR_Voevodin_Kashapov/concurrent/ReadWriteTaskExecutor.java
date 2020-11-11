package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.concurrent;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;

import java.awt.*;
import java.util.ArrayList;


public class ReadWriteTaskExecutor {
    public static void main(String[] args) throws InterruptedException {
        TabulatedFunction function = new LinkedListTabulatedFunction(new ZeroFunction(), 1, 10, 10);
        ArrayList<Thread> list = new ArrayList<>();
        ReadWriteTask myTask = new ReadWriteTask(function);
        for (int i = 0; i < 20; i++){
             list.add(new Thread(myTask));
        }

        for (Thread thread: list){
            thread.start();
        }
        Thread.sleep(2_000);

        System.out.println(function.toString());

    }
}
