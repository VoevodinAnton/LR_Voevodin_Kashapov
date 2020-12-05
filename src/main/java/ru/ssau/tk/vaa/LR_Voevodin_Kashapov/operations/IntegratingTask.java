package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;


public class IntegratingTask extends RecursiveTask<Double> {
    private static final long serialVersionUID = -3422831134405022851L;
    TabulatedFunction function;
    double xFrom;
    double xTo;
    public static final long threshold = 1;

    public IntegratingTask(TabulatedFunction function, double xFrom, double xTo) {
        this.function = function;
        this.xFrom = xFrom;
        this.xTo = xTo;
    }

    @Override
    protected Double compute() {
        double length = xTo - xFrom;
        if (length <= threshold){
            return add();
        }
        IntegratingTask firstTask = new IntegratingTask(function, xFrom, xFrom + length/2);
        firstTask.fork();

        IntegratingTask secondTask = new IntegratingTask(function, xFrom + length/2, xTo);
        System.out.println(xFrom);
        System.out.println(xTo);

        Double secondTaskResult = secondTask.compute();
        Double firstTaskResult = firstTask.join();
        return firstTaskResult + secondTaskResult;
    }

    private Double add(){
        SimpsonIntegratingMethod integral = new SimpsonIntegratingMethod();
        return integral.integrate(function, this.xFrom, this.xTo);
    }
}
