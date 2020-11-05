package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.StrictTabulatedFunction;

import static org.testng.Assert.*;

public class StrictTabulatedFunctionTest {
    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    private LinkedListTabulatedFunction getListOfArray() {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    @Test
    public void testApply() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();

        StrictTabulatedFunction strictFunction = new StrictTabulatedFunction(listOfArray);

        assertThrows(UnsupportedOperationException.class, () -> strictFunction.apply(-1));
        assertThrows(UnsupportedOperationException.class, () -> strictFunction.apply(2.5));

        assertEquals(strictFunction.getY(2), strictFunction.apply(3));
        assertEquals(strictFunction.getY(4), strictFunction.apply(5));
    }
}