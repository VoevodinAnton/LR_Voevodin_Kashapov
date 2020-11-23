package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

public class TabulatedIntegralOperator implements IntegralOperator<TabulatedFunction> {
    public double result;

    @Override
    public TabulatedFunction integrate(TabulatedFunction function, double xFrom, double xTo) {
        if (xFrom < function.leftBound() || xTo > function.rightBound()){
            throw new IllegalArgumentException("Incorrect bounds of integrating");
        }
        if (xFrom >= xTo) {
            throw new IllegalArgumentException("Incorrect bounds: xFrom >= xTo");
        }
        return null;
    }
}
