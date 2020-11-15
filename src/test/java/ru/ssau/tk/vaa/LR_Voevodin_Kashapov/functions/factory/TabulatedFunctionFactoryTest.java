package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.StrictTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.UnmodifiableTabulatedFunction;

import static org.testng.Assert.*;

public class TabulatedFunctionFactoryTest {
    private final double[] x = new double[]{1, 2, 3, 4, 5};
    private final double[] y = new double[]{2, 4, 6, 8, 10};

    @Test
    public void testCreateStrict() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction strictArr = arrayFactory.createStrict(x, y);
        TabulatedFunctionFactory listFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction strictLis = listFactory.createStrict(x, y);
        assertTrue(strictArr instanceof StrictTabulatedFunction);
        assertTrue(strictLis instanceof StrictTabulatedFunction);
    }

    @Test
    public void testCreateUnmodifiable() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction unmodifiableArr = arrayFactory.createUnmodifiable(x, y);
        TabulatedFunctionFactory listFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction unmodifiableLis = listFactory.createUnmodifiable(x, y);
        assertTrue(unmodifiableArr instanceof UnmodifiableTabulatedFunction);
        assertTrue(unmodifiableLis instanceof UnmodifiableTabulatedFunction);
    }

    @Test
    public void testCreateStrictUnmodifiable() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction strUnModArr = arrayFactory.createStrictUnmodifiable(x, y);
        TabulatedFunctionFactory listFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction strUnModLis = listFactory.createStrictUnmodifiable(x, y);
        assertTrue(strUnModArr instanceof UnmodifiableTabulatedFunction);
        assertThrows(UnsupportedOperationException.class, () -> strUnModArr.apply(0));
        assertThrows(UnsupportedOperationException.class, () -> strUnModArr.setY(2, 3 ));
        assertTrue(strUnModLis instanceof UnmodifiableTabulatedFunction);
        assertThrows(UnsupportedOperationException.class, () -> strUnModLis.apply(0));
        assertThrows(UnsupportedOperationException.class, () -> strUnModLis.setY(1, 2));
    }
}