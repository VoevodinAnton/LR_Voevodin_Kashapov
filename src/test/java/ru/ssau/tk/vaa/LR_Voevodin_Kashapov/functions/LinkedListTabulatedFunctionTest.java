package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.*;

public class LinkedListTabulatedFunctionTest {
    public static final double ACCURACY = 0.00001;

    private final MathFunction function = new SinFunction();

    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    private LinkedListTabulatedFunction getListOfMathFunc1() {
        return new LinkedListTabulatedFunction(function, 1, 5, 10);
    }

    private LinkedListTabulatedFunction getListOfMathFunc2() {
        return new LinkedListTabulatedFunction(function, -3, 3, 20);
    }

    private LinkedListTabulatedFunction getListOfMathFunc3() {
        return new LinkedListTabulatedFunction(function, 10, 20, 50);
    }

    private LinkedListTabulatedFunction getListOfArray() {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    @Test
    public void testAddNode() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        listOfArray.addNode(9, 99);
        assertEquals(listOfArray.rightBound(), 9, ACCURACY);
    }

    @Test
    public void testGetCount() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();
        LinkedListTabulatedFunction listOfMathFunc2 = getListOfMathFunc2();
        LinkedListTabulatedFunction listOfMathFunc3 = getListOfMathFunc3();

        assertEquals(listOfArray.getCount(), 5);
        assertEquals(listOfMathFunc1.getCount(), 10);
        assertEquals(listOfMathFunc2.getCount(), 20);
        assertEquals(listOfMathFunc3.getCount(), 50);
    }

    @Test
    public void testLeftBound() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();
        LinkedListTabulatedFunction listOfMathFunc2 = getListOfMathFunc2();
        LinkedListTabulatedFunction listOfMathFunc3 = getListOfMathFunc3();

        assertEquals(listOfMathFunc1.leftBound(), 1.0);
        assertEquals(listOfMathFunc2.leftBound(), -3.0);
        assertEquals(listOfMathFunc3.leftBound(), 10.0);
        assertEquals(listOfArray.leftBound(), 1.0);
    }

    @Test
    public void testRightBound() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();
        LinkedListTabulatedFunction listOfMathFunc2 = getListOfMathFunc2();
        LinkedListTabulatedFunction listOfMathFunc3 = getListOfMathFunc3();

        assertEquals(listOfArray.rightBound(), 5.0);

        assertEquals(listOfMathFunc1.rightBound(), 5.0, ACCURACY);
        assertEquals(listOfMathFunc2.rightBound(), 3.0, ACCURACY);
        assertEquals(listOfMathFunc3.rightBound(), 20.0, ACCURACY);
    }

    @Test
    public void testGetNode() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();

        assertEquals(listOfArray.getNode(2).x, 3, ACCURACY);
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getNode(100));
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getNode(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getNode(5));

    }

    @Test
    public void testGetX() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();
        LinkedListTabulatedFunction listOfMathFunc2 = getListOfMathFunc2();
        LinkedListTabulatedFunction listOfMathFunc3 = getListOfMathFunc3();

        assertEquals(listOfMathFunc1.getX(0), 1.0);
        assertEquals(listOfMathFunc1.getX(9), 5.0, ACCURACY);
        assertEquals(listOfMathFunc2.getX(0), -3.0);
        assertEquals(listOfMathFunc3.getX(0), 10.0);

        assertEquals(listOfArray.getX(0), 1.0);
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getX(100));
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getX(5));
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getX(-1));
    }

    @Test
    public void testGetY() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();
        LinkedListTabulatedFunction listOfMathFunc2 = getListOfMathFunc2();
        LinkedListTabulatedFunction listOfMathFunc3 = getListOfMathFunc3();

        assertEquals(listOfMathFunc1.getY(0), 0.841470984807, ACCURACY);
        assertEquals(listOfMathFunc2.getY(0), -0.1411200081, ACCURACY);
        assertEquals(listOfMathFunc3.getY(0), -0.5440211109, ACCURACY);

        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getY(100));
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getY(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().getY(5));

    }

    @Test
    public void testSetY() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();

        listOfArray.setY(2, 39);
        assertEquals(listOfArray.getY(2), 39, ACCURACY);

        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().setY(100, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().setY(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> getListOfArray().setY(5, 1));
    }

    @Test
    public void testIndexOfX() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();

        assertEquals(listOfArray.indexOfX(4), 3);
        assertEquals(listOfMathFunc1.indexOfX(3), -1);
        assertEquals(listOfMathFunc1.indexOfX(100), -1);
    }

    @Test
    public void testIndexOfY() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();

        assertEquals(listOfArray.indexOfY(4), 1);
        assertEquals(listOfArray.indexOfY(6), 2);
        assertEquals(listOfArray.indexOfY(8), 3);
    }

    @Test
    public void testFloorIndexOfX() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();

        assertEquals(listOfArray.floorIndexOfX(1.1), 0);
        assertEquals(listOfArray.floorIndexOfX(3.2), 2);
        assertEquals(listOfMathFunc1.floorIndexOfX(1.1), 0);
        assertEquals(listOfMathFunc1.floorIndexOfX(100), 10);

        assertThrows(IllegalArgumentException.class, () -> getListOfArray().floorIndexOfX(-1));
        assertThrows(IllegalArgumentException.class, () -> getListOfArray().floorIndexOfX(0));

    }

    @Test
    public void testExtrapolateLeft() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();
        LinkedListTabulatedFunction listOfMathFunc2 = getListOfMathFunc2();

        assertEquals(listOfMathFunc2.extrapolateLeft(-4), 0.810402615850074, ACCURACY);
        assertEquals(listOfMathFunc1.extrapolateLeft(0), 0.502717214644860, ACCURACY);
        assertEquals(listOfArray.extrapolateLeft(0), 0.0, ACCURACY);
    }

    @Test
    public void testExtrapolateRight() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();

        assertEquals(listOfMathFunc1.extrapolateRight(6), -0.894118534015851, ACCURACY);
        assertEquals(listOfMathFunc1.extrapolateRight(7), -0.829312793368563, ACCURACY);
        assertEquals(listOfArray.extrapolateRight(6), 12, ACCURACY);
    }

    @Test
    public void testInterpolate() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();

        assertEquals(listOfArray.interpolate(4, 3), 8.0);
        assertEquals(listOfArray.interpolate(3, 2), 6.0);
    }

    @Test
    public void testFloorNodeOfX() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();

        assertEquals(listOfArray.floorNodeOfX(listOfArray.getX(0)).x, 1.0);
        assertEquals(listOfArray.floorNodeOfX(1).x, 1.0);
        assertEquals(listOfArray.floorNodeOfX(10).x, 5.0);

        assertThrows(IllegalArgumentException.class, () -> getListOfArray().floorNodeOfX(0));
        assertThrows(IllegalArgumentException.class, () -> getListOfArray().floorNodeOfX(-1));
    }

    @Test
    public void testApply() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        LinkedListTabulatedFunction listOfMathFunc1 = getListOfMathFunc1();
        LinkedListTabulatedFunction listOfMathFunc2 = getListOfMathFunc2();

        assertEquals(listOfMathFunc1.apply(6), -0.894118534015851, ACCURACY);
        assertEquals(listOfMathFunc2.apply(-4), 0.810402615850074, ACCURACY);
        assertEquals(listOfArray.apply(5), 10.0, ACCURACY);

    }

    @Test
    public void testCompositeFunction() {
        MathFunction listOfMathFunc1 = getListOfMathFunc1();
        MathFunction listOfMathFunc2 = getListOfMathFunc2();

        assertEquals(listOfMathFunc1.andThen(listOfMathFunc2).apply(2), 0.770258, ACCURACY);

    }

    @Test
    public void testInsert() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();

        //out of list on the left
        listOfArray.insert(0, 1);
        assertEquals(listOfArray.indexOfX(0), 0);

        assertEquals(listOfArray.getX(0), 0, ACCURACY);
        assertEquals(listOfArray.getX(1), 1, ACCURACY);
        assertEquals(listOfArray.getX(2), 2, ACCURACY);
        assertEquals(listOfArray.getX(3), 3, ACCURACY);
        assertEquals(listOfArray.getX(4), 4, ACCURACY);
        assertEquals(listOfArray.getX(5), 5, ACCURACY);


        LinkedListTabulatedFunction listOfArray1 = getListOfArray();

        //inside the list
        listOfArray1.insert(1.5, 1);
        assertEquals(listOfArray1.indexOfX(1.5), 1);

        assertEquals(listOfArray1.getX(0), 1, ACCURACY);
        assertEquals(listOfArray1.getX(1), 1.5, ACCURACY);
        assertEquals(listOfArray1.getX(2), 2, ACCURACY);
        assertEquals(listOfArray1.getX(3), 3, ACCURACY);
        assertEquals(listOfArray1.getX(4), 4, ACCURACY);
        assertEquals(listOfArray1.getX(5), 5, ACCURACY);

        LinkedListTabulatedFunction listOfArray2 = getListOfArray();

        //out of list on the right
        listOfArray2.insert(9, 5);
        assertEquals(listOfArray2.indexOfX(9), 5);

        assertEquals(listOfArray2.getX(0), 1, ACCURACY);
        assertEquals(listOfArray2.getX(1), 2, ACCURACY);
        assertEquals(listOfArray2.getX(2), 3, ACCURACY);
        assertEquals(listOfArray2.getX(3), 4, ACCURACY);
        assertEquals(listOfArray2.getX(4), 5, ACCURACY);
        assertEquals(listOfArray2.getX(5), 9, ACCURACY);
    }


    @Test
    public void testRemove() {
        //out of list on the left
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        //listOfArray = [1, 2, 3, 4, 5]
        listOfArray.remove(0);

        assertEquals(listOfArray.getX(0), 2, ACCURACY);
        assertEquals(listOfArray.getX(1), 3, ACCURACY);
        assertEquals(listOfArray.getX(2), 4, ACCURACY);
        assertEquals(listOfArray.getX(3), 5, ACCURACY);

        //inside the list
        LinkedListTabulatedFunction listOfArray1 = getListOfArray();

        listOfArray1.remove(4);

        assertEquals(listOfArray1.getX(0), 1, ACCURACY);
        assertEquals(listOfArray1.getX(1), 2, ACCURACY);
        assertEquals(listOfArray1.getX(2), 3, ACCURACY);
        assertEquals(listOfArray1.getX(3), 4, ACCURACY);


        //out of list on the right
        LinkedListTabulatedFunction listOfArray2 = getListOfArray();

        listOfArray2.remove(3);

        assertEquals(listOfArray2.getX(0), 1, ACCURACY);
        assertEquals(listOfArray2.getX(1), 2, ACCURACY);
        assertEquals(listOfArray2.getX(2), 3, ACCURACY);
        assertEquals(listOfArray2.getX(3), 5, ACCURACY);
    }

    @Test
    public void testIteratorFirst() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        Iterator<Point> iterator = listOfArray.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(listOfArray.getX(i++), point.x, ACCURACY);
        }

    }

    @Test
    public void testIteratorSecond() {
        LinkedListTabulatedFunction listOfArray = getListOfArray();
        Iterator<Point> iterator = listOfArray.iterator();
        int i = 0;
        for (Point point : listOfArray) {
            assertEquals(listOfArray.getX(i++), point.x, ACCURACY);
        }
    }


}
