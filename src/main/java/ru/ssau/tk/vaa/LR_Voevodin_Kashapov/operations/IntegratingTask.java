package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import java.util.concurrent.Callable;


public class IntegratingTask implements Callable<Double> {
    TabulatedFunction function;
    double step;
    int index;
    private Runnable postRunAction;

    public IntegratingTask(TabulatedFunction function, int step, int index, Runnable postRunAction) {
        this.function = function;
        this.step = step;
        this.index = index;
        this.postRunAction = postRunAction;
    }


    @Override
    public Double call() throws RuntimeException {
        double deltaX = (function.rightBound() - function.leftBound()) / step;
        double start = function.leftBound() + index * deltaX;
        double finish = start + deltaX;
        SimpsonIntegratingMethod integral = new SimpsonIntegratingMethod();
        postRunAction.run();
        return integral.integrate(function, start, finish);
    }
}
