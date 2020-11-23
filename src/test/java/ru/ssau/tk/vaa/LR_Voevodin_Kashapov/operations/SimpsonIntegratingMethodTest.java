package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ArrayTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;

import static org.testng.Assert.*;

public class SimpsonIntegratingMethodTest {
    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    private LinkedListTabulatedFunction getList() {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    private ArrayTabulatedFunction getArray() {
        return new ArrayTabulatedFunction(xValues, yValues);
    }

    @Test
    public void testIntegrate() {
        SimpsonIntegratingMethod sum = new SimpsonIntegratingMethod();
        assertEquals(sum.integrate(getArray(), 1, 5), 24.0, 0.1);
        //assertEquals(sum.integrate(getList(), 1, 5), 24.0);
    }
}