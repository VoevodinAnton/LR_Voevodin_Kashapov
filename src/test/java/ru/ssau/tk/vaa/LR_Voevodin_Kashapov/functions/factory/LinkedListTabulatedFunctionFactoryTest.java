package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import static org.testng.Assert.*;

public class LinkedListTabulatedFunctionFactoryTest {
    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    @Test
    public void testCreate() {
        var function = new LinkedListTabulatedFunctionFactory().create(xValues, yValues);

        assertTrue(function instanceof LinkedListTabulatedFunction);
    }
}