package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

public class SimpsonIntegratingMethod implements IntegratingOperator<TabulatedFunction> {

    @Override
    public double integrate(TabulatedFunction function, double xFrom, double xTo) {

        double splitting = 8192;
        double h = (xTo - xFrom) / (splitting - 1);

        double integral = function.apply(xFrom) + function.apply(xTo);
        double xOdd = xFrom;
        double xEven = xFrom;
        for (int j = 1; j < splitting / 2 - 1; j++) {
            xEven += h * 2 * j;
            xOdd += h * (2 * j - 1);
            integral += 2 * function.apply(xEven) + 4 * function.apply(xOdd);
        }
        integral += function.apply(xOdd + 2 * h);
        return integral;
    }
}
