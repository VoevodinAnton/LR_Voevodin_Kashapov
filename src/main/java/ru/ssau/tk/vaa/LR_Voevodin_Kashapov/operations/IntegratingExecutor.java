package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ConstantFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.SqrFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import java.util.concurrent.*;

public class IntegratingExecutor {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Double> result = new CopyOnWriteArrayList<>();
        double integral = 0;
        ForkJoinPool pool = new ForkJoinPool();
        TabulatedFunction function = new LinkedListTabulatedFunction(new ConstantFunction(2), 0, 10, 100000);

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(processors + " processor"
                + (processors != 1 ? "s are " : " is ")
                + "available");

        IntegratingTask task = new IntegratingTask(function, 0, 10, result);

        long startTime = System.currentTimeMillis();
        pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("The integral was calculated in " + (endTime - startTime) +
                " milliseconds.");

        for (double s : result) {
            integral = integral + s;
        }

        System.out.println(integral);
    }
}
