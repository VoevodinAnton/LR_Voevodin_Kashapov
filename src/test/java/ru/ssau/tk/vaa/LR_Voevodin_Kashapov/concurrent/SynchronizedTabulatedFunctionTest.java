package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.concurrent;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;

import static org.testng.Assert.*;

public class SynchronizedTabulatedFunctionTest {
    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};
    private Object mutex = new Object();

    private SynchronizedTabulatedFunction getSynchronizedList(){
        return new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xValues, yValues), mutex);
    }


    @Test
    public void testGetCount() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();

        assertEquals(synchronizedTabulatedFunction.getCount(), 5);
    }

    @Test
    public void testGetX() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();

        assertEquals(synchronizedTabulatedFunction.getX(0), 1.0);
    }

    @Test
    public void testGetY() {
    }

    @Test
    public void testSetY() {
    }

    @Test
    public void testIndexOfX() {
    }

    @Test
    public void testIndexOfY() {
    }

    @Test
    public void testLeftBound() {
    }

    @Test
    public void testRightBound() {
    }

    @Test
    public void testIterator() {
    }

    @Test
    public void testApply() {
    }
}