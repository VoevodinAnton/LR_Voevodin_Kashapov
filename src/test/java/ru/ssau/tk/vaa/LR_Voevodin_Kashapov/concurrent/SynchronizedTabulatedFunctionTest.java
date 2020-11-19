package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.concurrent;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ArrayTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.Point;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.testng.Assert.*;

public class SynchronizedTabulatedFunctionTest {
    public static final double ACCURACY = 0.00001;

    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};
    private final Object mutex = new Object();

    private SynchronizedTabulatedFunction getSynchronizedList() {
        return new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xValues, yValues), mutex);
    }

    private SynchronizedTabulatedFunction getSynchronizedArray() {
        return new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues), mutex);
    }

    @Test
    public void testGetCount() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();

        assertEquals(synchronizedTabulatedFunction.getCount(), 5);
        assertEquals(synchronizedArr.getCount(), 5);
    }

    @Test
    public void testGetX() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();

        assertEquals(synchronizedTabulatedFunction.getX(0), 1.0);
        assertEquals(synchronizedArr.getX(3), 4.0);
    }

    @Test
    public void testGetY() {
        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();
        assertEquals(synchronizedArr.getY(3), 8.0);

        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        assertEquals(synchronizedTabulatedFunction.getY(0), 2.0);
    }

    @Test
    public void testSetY() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        synchronizedTabulatedFunction.setY(2, 39);
        assertEquals(synchronizedTabulatedFunction.getY(2), 39, ACCURACY);

        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();
        synchronizedArr.setY(3, 12);
        assertEquals(synchronizedArr.getY(3), 12.0);
    }

    @Test
    public void testIndexOfX() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        assertEquals(synchronizedTabulatedFunction.indexOfX(4), 3);

        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();
        assertEquals(synchronizedArr.indexOfX(4), 3);
    }

    @Test
    public void testIndexOfY() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        assertEquals(synchronizedTabulatedFunction.indexOfY(4), 1);
        assertEquals(synchronizedTabulatedFunction.indexOfY(6), 2);
        assertEquals(synchronizedTabulatedFunction.indexOfY(8), 3);

        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();
        assertEquals(synchronizedArr.indexOfY(4), 1);
        assertEquals(synchronizedArr.indexOfY(6), 2);
        assertEquals(synchronizedArr.indexOfY(8), 3);
    }

    @Test
    public void testLeftBound() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        assertEquals(synchronizedTabulatedFunction.leftBound(), 1.0);

        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();
        assertEquals(synchronizedArr.leftBound(), 1.0);
    }

    @Test
    public void testRightBound() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        assertEquals(synchronizedTabulatedFunction.rightBound(), 5.0);

        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();
        assertEquals(synchronizedArr.rightBound(), 5.0);
    }

    @Test
    public void testIteratorWhile() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedArray();
        Iterator<Point> it1 = synchronizedTabulatedFunction.iterator();
        int i = 0;
        while (it1.hasNext()) {
            Point a = it1.next();
            assertEquals(synchronizedTabulatedFunction.getX(i), a.x);
            assertEquals(synchronizedTabulatedFunction.getY(i++), a.y);
        }
        assertEquals(synchronizedTabulatedFunction.getCount(), i);
        assertThrows(NoSuchElementException.class, it1::next);

    }

    @Test
    public void testIteratorForEach() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        int i = 0;
        for (Point a : synchronizedTabulatedFunction) {
            assertEquals(a.x, synchronizedTabulatedFunction.getX(i));
            assertEquals(a.y, synchronizedTabulatedFunction.getY(i++));
        }
        assertEquals(synchronizedTabulatedFunction.getCount(), i);
    }


    @Test
    public void testApply() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        assertEquals(synchronizedTabulatedFunction.apply(5), 10.0, ACCURACY);

        SynchronizedTabulatedFunction synchronizedArr = getSynchronizedArray();
        assertEquals(synchronizedArr.apply(6), 12.0);
    }

    @Test
    public void testDoSynchronously() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedList();
        assertEquals((int) synchronizedTabulatedFunction.doSynchronously(SynchronizedTabulatedFunction::getCount), 5);
        assertEquals(synchronizedTabulatedFunction.doSynchronously(SynchronizedTabulatedFunction::leftBound), 1.0);

    }
}