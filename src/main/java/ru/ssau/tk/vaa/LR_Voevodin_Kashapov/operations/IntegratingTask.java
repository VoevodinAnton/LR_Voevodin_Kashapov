package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.MathFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

public class IntegratingTask implements Runnable {
    TabulatedFunction function;
    double xFrom;
    double xTo;
    double step;
    int index;
    double[] results;

    //ToDo: переделать конструктор, попытаться выкинуть некоторые параметры
    public IntegratingTask(TabulatedFunction function, double xFrom, double xTo, int step, int index, double[] results) {
        this.function = function;
        this.step = step;
        this.xFrom = xFrom;
        this.xTo = xTo;
        this.index = index;
        this.results = results;
    }


    @Override
    public void run() {
        double deltaX = (xTo - xFrom) / step;
        double start = xFrom + index * deltaX;
        double finish = start + deltaX;
        //ToDo: написать через лямбда выражение
        SimpsonIntegratingMethod integral = new SimpsonIntegratingMethod();
        results[index] = integral.integrate(function, start, finish);
    }
}
