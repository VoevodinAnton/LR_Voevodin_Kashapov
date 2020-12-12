package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveAction;


public class IntegratingTask extends RecursiveAction {
    private static final long serialVersionUID = -3422831134405022851L;
    private TabulatedFunction function;
    private double xFrom;
    private double xTo;
    private static final double threshold = 1;
    private CopyOnWriteArrayList<Double> result;

    public IntegratingTask(TabulatedFunction function, double xFrom, double xTo, CopyOnWriteArrayList<Double> result) {
        this.function = function;
        this.xFrom = xFrom;
        this.xTo = xTo;
        this.result = result;
    }

    @Override
    protected void compute() {
        double length = xTo - xFrom;
        if (length <= threshold) {
            result.add(computeDirectly());
            return;
        }
        double split = length / 2;
        invokeAll(new IntegratingTask(function, xFrom, xFrom + split, result), new IntegratingTask(function, xFrom + split, xTo, result));

    }

    private Double computeDirectly() {
        SimpsonIntegratingMethod integral = new SimpsonIntegratingMethod();
        return integral.integrate(function, xFrom, xTo);
    }
}
