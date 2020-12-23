package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ConstantFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class IntegratingExecutor {
    public static void main(String[] args) {
        List<Double> result = new ArrayList<>();
        double integral = 0;
        ForkJoinPool pool = new ForkJoinPool();
        TabulatedFunction function = new LinkedListTabulatedFunction(new ConstantFunction(2), 0, 100, 100000);

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(processors + " processor"
                + (processors != 1 ? "s are " : " is ")
                + "available");

        SimpsonIntegratingMethod method = new SimpsonIntegratingMethod();
        long startTime1 = System.currentTimeMillis();
        double integralOneThread = method.integrate(function, 0, 100);
        long endTime1 = System.currentTimeMillis();
        System.out.println(integralOneThread);
        System.out.println("integral in single-stream mode was calculated in " + (endTime1 - startTime1) +
                " milliseconds.");

        IntegratingTask task = new IntegratingTask(function, 0, 100, result);

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
