package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.factory;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ArrayTabulatedFunction;

import static org.testng.Assert.*;

public class ArrayTabulatedFunctionFactoryTest {

    public static final double ACCURACY = 0.00001;

    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    @Test
    public void testCreate() {
        var function = new ArrayTabulatedFunctionFactory().create(xValues, yValues);

        assertTrue(function instanceof ArrayTabulatedFunction);
    }

    @Test
    public void testCreateStrict() {
        var functionStrictArray = new ArrayTabulatedFunctionFactory().createStrict(xValues, yValues);

        assertEquals(functionStrictArray.apply(1), 2, ACCURACY);
        assertEquals(functionStrictArray.apply(5), 10, ACCURACY);
        assertThrows(UnsupportedOperationException.class, () -> functionStrictArray.apply(-1));
        assertThrows(UnsupportedOperationException.class, () -> functionStrictArray.apply(2.5));
    }
}