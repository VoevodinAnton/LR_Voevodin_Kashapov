package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

public class SimpsonIntegratingMethod implements IntegratingOperator<TabulatedFunction> {
    private final double ACCURACY = 0.0000001;

    @Override
    public double integrate(TabulatedFunction function, double xFrom, double xTo) {
        if (xFrom < function.leftBound() - ACCURACY) {
            throw new IllegalArgumentException("Incorrect low bound of integrating");
        }
        if (xTo > function.rightBound() + ACCURACY) {
            throw new IllegalArgumentException("Incorrect high bounds of integrating");
        }
        if (xFrom >= xTo) {
            throw new IllegalArgumentException("Incorrect bounds: xFrom >= xTo");
        }

        double n = 8196;
        double h = (xTo - xFrom) / (n - 1);
        double xEven;
        double xOdd;
        double integral = function.apply(xFrom) + function.apply(xTo);
        for (int j = 1; j < n / 2 - 1; j++) {
            xEven = xFrom + h * 2 * j;
            xOdd = xFrom + h * (2 * j - 1);
            integral += 2 * function.apply(xEven) + 4 * function.apply(xOdd);
        }
        integral += function.apply(xFrom + (n - 1) * h);
        return integral * h / 3;
    }

}
