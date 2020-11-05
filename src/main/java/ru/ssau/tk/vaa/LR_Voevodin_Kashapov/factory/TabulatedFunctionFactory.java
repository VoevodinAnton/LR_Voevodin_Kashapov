package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.factory;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);

    TabulatedFunction createStrict(double[] xValues, double[] yValues);
}
