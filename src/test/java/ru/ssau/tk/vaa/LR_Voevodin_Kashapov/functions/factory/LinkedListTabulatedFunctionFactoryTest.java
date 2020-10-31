package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import static org.testng.Assert.*;

public class LinkedListTabulatedFunctionFactoryTest {
    public static final double ACCURACY = 0.00001;

    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    @Test
    public void testCreate() {
        var function = new LinkedListTabulatedFunctionFactory().create(xValues, yValues);

        assertTrue(function instanceof LinkedListTabulatedFunction);
    }

    @Test
    public void testCreateStrict() {
        var functionStrictList = new LinkedListTabulatedFunctionFactory().createStrict(xValues, yValues);

        assertEquals(functionStrictList.apply(1), 2, ACCURACY);
        assertEquals(functionStrictList.apply(5), 10, ACCURACY);
        assertThrows(UnsupportedOperationException.class, () -> functionStrictList.apply(-1));
        assertThrows(UnsupportedOperationException.class, () -> functionStrictList.apply(2.5));

    }
}