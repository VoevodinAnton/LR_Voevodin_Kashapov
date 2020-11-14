package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.*;

public class UnmodifiableTabulatedFunctionTest {

    private final double[] x = new double[]{1, 4, 9, 16};
    private final double[] y = new double[]{1, 16, 81, 256};

    private ArrayTabulatedFunction array() {
        return new ArrayTabulatedFunction(x, y);
    }

    private LinkedListTabulatedFunction list() {
        return new LinkedListTabulatedFunction(x, y);
    }

    @Test
    public void testGetCount() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        assertEquals(arr.getCount(), 4);

        UnmodifiableTabulatedFunction list = new UnmodifiableTabulatedFunction(list());
        assertEquals(list.getCount(), 4);
    }

    @Test
    public void testGetX() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction list = new UnmodifiableTabulatedFunction(list());

        assertEquals(arr.getX(0), 1.0);
        assertEquals(list.getX(0), 1.0);
        assertEquals(arr.getX(1), 4.0);
        assertEquals(list.getX(1), 4.0);
        assertEquals(arr.getX(2), 9.0);
        assertEquals(list.getX(2), 9.0);
        assertEquals(arr.getX(3), 16.0);
        assertEquals(list.getX(3), 16.0);
    }

    @Test
    public void testGetY() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction lis = new UnmodifiableTabulatedFunction(list());

        assertEquals(arr.getY(0), 1.0);
        assertEquals(lis.getY(0), 1.0);
        assertEquals(arr.getY(1), 16.0);
        assertEquals(lis.getY(1), 16.0);
        assertEquals(arr.getY(2), 81.0);
        assertEquals(lis.getY(2), 81.0);
        assertEquals(arr.getY(3), 256.0);
        assertEquals(lis.getY(3), 256.0);
    }

    @Test
    public void testSetY() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction lis = new UnmodifiableTabulatedFunction(list());

        assertThrows(UnsupportedOperationException.class, () -> arr.setY(0, 10));
        assertThrows(UnsupportedOperationException.class, () -> lis.setY(1, 15));
        assertThrows(UnsupportedOperationException.class, () -> arr.setY(2, 20));
        assertThrows(UnsupportedOperationException.class, () -> lis.setY(3, 25));
    }

    @Test
    public void testIndexOfX() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction list = new UnmodifiableTabulatedFunction(list());

        assertEquals(arr.indexOfX(1), 0);
        assertEquals(arr.indexOfX(4), 1);
        assertEquals(arr.indexOfX(9), 2);
        assertEquals(arr.indexOfX(16), 3);
        assertEquals(list.indexOfX(1), 0);
        assertEquals(list.indexOfX(4), 1);
        assertEquals(list.indexOfX(9), 2);
        assertEquals(list.indexOfX(16), 3);
    }

    @Test
    public void testIndexOfY() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction lis = new UnmodifiableTabulatedFunction(list());

        assertEquals(arr.indexOfY(1), 0);
        assertEquals(arr.indexOfY(16), 1);
        assertEquals(arr.indexOfY(81), 2);
        assertEquals(arr.indexOfY(256), 3);
        assertEquals(lis.indexOfY(1), 0);
        assertEquals(lis.indexOfY(16), 1);
        assertEquals(lis.indexOfY(81), 2);
        assertEquals(lis.indexOfY(256), 3);
    }

    @Test
    public void testLeftBound() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction list = new UnmodifiableTabulatedFunction(list());

        assertEquals(arr.leftBound(), 1.0);
        assertEquals(list.leftBound(), 1.0);
    }

    @Test
    public void testRightBound() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction list = new UnmodifiableTabulatedFunction(list());

        assertEquals(arr.rightBound(), 16.0);
        assertEquals(list.rightBound(), 16.0);
    }

    @Test
    public void testIterator() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction list = new UnmodifiableTabulatedFunction(list());

        Iterator<Point> itArr = arr.iterator();
        int i = 0;
        for (Point abc : arr) {
            abc = itArr.next();
            assertEquals(arr.getX(i), abc.x);
            assertEquals(arr.getY(i++), abc.y);
        }
        Iterator<Point> itLis = list.iterator();
        int j = 0;
        for (Point abc : list) {
            abc = itLis.next();
            assertEquals(list.getX(j), abc.x);
            assertEquals(list.getY(j++), abc.y);
        }
    }

    @Test
    public void testApply() {
        UnmodifiableTabulatedFunction arr = new UnmodifiableTabulatedFunction(array());
        UnmodifiableTabulatedFunction list = new UnmodifiableTabulatedFunction(list());

        assertEquals(arr.apply(1), 1.0);
        assertEquals(list.apply(2), 6.0);
        assertEquals(arr.apply(3), 11.0);
        assertEquals(list.apply(4), 16.0);

    }
}