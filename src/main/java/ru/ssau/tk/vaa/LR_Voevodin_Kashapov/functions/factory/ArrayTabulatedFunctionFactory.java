package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.ArrayIsNotSortedException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ArrayTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.MathFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.StrictTabulatedFunction;

import java.util.List;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        checkSorted(xValues);
        return new ArrayTabulatedFunction(xValues, yValues);
    }

    @Override
    public TabulatedFunction create(MathFunction function, double xFrom, double xTo, int count) {
        return new ArrayTabulatedFunction(function, xFrom, xTo, count);
    }

    public void checkSorted(double[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] <= array[i]) {
                throw new ArrayIsNotSortedException("xValues is not sorted");
            }
        }
    }
}
