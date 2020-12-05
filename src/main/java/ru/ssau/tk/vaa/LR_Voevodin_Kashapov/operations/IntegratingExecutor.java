package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.SinFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class IntegratingExecutor {
    public static void main(String[] args) {
        int countFuture = 10;
        double result = 0;
        CountDownLatch countDownLatch = new CountDownLatch(countFuture);
        ExecutorService executor = Executors.newFixedThreadPool(countFuture);
        List<Future<Double>> list = new ArrayList<>();
        TabulatedFunction function = new LinkedListTabulatedFunction(new SinFunction(), 0, 2 * Math.PI, 10000);
        for (int i = 0; i < countFuture; i++) {
            Callable<Double> myTask = new IntegratingTask(function, countFuture, i, countDownLatch::countDown);

            Future<Double> future = executor.submit(myTask);
            list.add(future);
        }

        for (Future<Double> future : list) {
            try {
                result += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }
        System.out.println(result);
        executor.shutdown();
    }
}
