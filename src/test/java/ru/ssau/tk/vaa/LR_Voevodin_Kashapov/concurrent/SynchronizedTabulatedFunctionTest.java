package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.concurrent;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;

import static org.testng.Assert.*;

public class SynchronizedTabulatedFunctionTest {
    public static final double ACCURACY = 0.00001;

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
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();

        assertEquals(synchronizedTabulatedFunction.getY(0), 2.0);
    }

    @Test
    public void testSetY() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();

        synchronizedTabulatedFunction.setY(2, 39);
        assertEquals(synchronizedTabulatedFunction.getY(2), 39, ACCURACY);
    }

    @Test
    public void testIndexOfX() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();

        assertEquals(synchronizedTabulatedFunction.indexOfX(4), 3);
    }

    @Test
    public void testIndexOfY() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();


    }

    @Test
    public void testLeftBound() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
    }

    @Test
    public void testRightBound() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
    }

    @Test
    public void testIterator() {
    }

    @Test
    public void testApply() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
    }
}