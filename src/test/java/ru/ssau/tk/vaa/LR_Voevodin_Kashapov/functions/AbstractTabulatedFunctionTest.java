package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

import exeptions.*;

public class AbstractTabulatedFunctionTest {
    private final static double ACCURACY = 0.00001;
    MockTabulatedFunction mock = new MockTabulatedFunction();

    @Test
    public void testInterpolate() {

        assertEquals(mock.interpolate(2.0, 1.0, 5.0, 4.0, 2.0), 3.5, ACCURACY);
        assertEquals(mock.interpolate(5.0, 3.0, 7.0, 6.0, 8.0), 7, ACCURACY);
        assertEquals(mock.interpolate(3.0, 2.71828182854, 3.1415926535, 3.1415926535, 2.71828182854), 2.859874482040000, ACCURACY);
        assertEquals(mock.interpolate(9.0, 2.71828182854 * 2.71828182854, 3.1415926535 * 3.1415926535, 3.1415926535, 2.71828182854), 2.866681662562116, ACCURACY);
        assertEquals(mock.interpolate(2.0, 1.0, 3.0, 10.0, 10.0), 10, ACCURACY);
    }

    @Test
    public void applyTest() {
        assertEquals(mock.apply(5), 2.0, ACCURACY);
        assertEquals(mock.apply(7), 1.0, ACCURACY);
        assertEquals(mock.apply(3), 3.0, ACCURACY);
        assertEquals(mock.apply(1), 4.0, ACCURACY);
        assertEquals(mock.apply(0), 4.5, ACCURACY);
    }


    @Test
    public void testCheckLengthIsTheSame() {
        assertThrows(DifferentLengthOfArraysException.class, () -> {
            double[] x = new double[]{1, 4, 9, 16};
            double[] y = new double[]{1, 16, 81, 256, 22};
            AbstractTabulatedFunction.checkLengthIsTheSame(x, y);
        });
    }

    @Test
    public void testCheckSorted() {
        assertThrows(ArrayIsNotSortedException.class, () -> {
            double[] x = new double[]{1, 4, 33, 9, 16};
            AbstractTabulatedFunction.checkSorted(x);
        });
    }

    @Test
    public void testTestToString() {
        double[] xValues = {1, 2, 3, 4};
        double[] yValues = {2, 4, 6, 8};
        TabulatedFunction functionOfList = new LinkedListTabulatedFunction(xValues, yValues);
        assertEquals(functionOfList.toString(), "LinkedListTabulatedFunction size = 4\n[1.0; 2.0]\n[2.0; 4.0]\n[3.0; 6.0]\n[4.0; 8.0]");
    }
}
