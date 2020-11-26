package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ConstantFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.SinFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import java.util.ArrayList;
import java.util.List;

public class IntegratingExecutor {
    public static void main(String[] args) throws InterruptedException {
        int countThread = 2;
        double[] results = new double[countThread];
        double result = 0;
        TabulatedFunction function = new LinkedListTabulatedFunction(new SinFunction(), 0, 2 * Math.PI, 10000);
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < countThread; i++) {
            IntegratingTask myTask = new IntegratingTask(function, 0, 2 * Math.PI, countThread, i, results);
            list.add(new Thread(myTask));
        }

        for (Thread thread : list) {
            thread.start();
        }
        //ToDo: переделать через CountDownLatch
        Thread.sleep(2000);

        for (int i = 0; i < countThread; i++) {
            result += results[i];
        }

        System.out.println(result);
    }
}
