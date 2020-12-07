package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ConstantFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import java.util.concurrent.*;

public class IntegratingExecutor {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        TabulatedFunction function = new LinkedListTabulatedFunction(new ConstantFunction(2), 0, 6, 10000);

        ForkJoinTask<Double> task = new IntegratingTask(function, 0, 6);
        System.out.println(pool.invoke(task));
    }
}
