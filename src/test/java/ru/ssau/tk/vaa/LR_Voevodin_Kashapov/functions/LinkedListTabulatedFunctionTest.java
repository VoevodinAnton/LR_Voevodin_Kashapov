package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LinkedListTabulatedFunctionTest {
    public static final double ACCURACY = 0.00001;

    MathFunction function = new SinFunction();
    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    LinkedListTabulatedFunction listOfMathFunc1 = new LinkedListTabulatedFunction(function, 1, 5, 10);
    LinkedListTabulatedFunction listOfMathFunc2 = new LinkedListTabulatedFunction(function, -3, 3, 20);
    LinkedListTabulatedFunction listOfMathFunc3 = new LinkedListTabulatedFunction(function, 10, 20, 50);
    LinkedListTabulatedFunction listOfArray = new LinkedListTabulatedFunction(xValues, yValues);

    @Test
    public void testGetCount() {
        assertEquals(listOfArray.getCount(), 5);
        assertEquals(listOfMathFunc1.getCount(), 10);
        assertEquals(listOfMathFunc2.getCount(), 20);
        assertEquals(listOfMathFunc3.getCount(), 50);
    }

    @Test
    public void testLeftBound() {
        assertEquals(listOfMathFunc1.leftBound(), 1.0);
        assertEquals(listOfMathFunc2.leftBound(), -3.0);
        assertEquals(listOfMathFunc3.leftBound(), 10.0);

        assertEquals(listOfArray.leftBound(), 5.0);
    }

    @Test
    public void testRightBound() {
        assertEquals(listOfArray.rightBound(), 5.0);

        assertEquals(listOfMathFunc1.rightBound(), 5.0, ACCURACY);
        assertEquals(listOfMathFunc2.rightBound(), 3.0, ACCURACY);
        assertEquals(listOfMathFunc3.rightBound(), 20.0, ACCURACY);
    }

    @Test
    public void testGetNode() {
    }

    @Test
    public void testGetX() {
        assertEquals(listOfMathFunc1.getX(0), 1.0);
        assertEquals(listOfMathFunc1.getX(9), 5.0, ACCURACY);
        assertEquals(listOfMathFunc2.getX(0), -3.0);
        assertEquals(listOfMathFunc3.getX(0), 10.0);

        assertEquals(listOfArray.getX(0), 1.0);
    }

    @Test
    public void testGetY() {
        assertEquals(listOfMathFunc1.getY(0), 0.841470984807, ACCURACY);
        assertEquals(listOfMathFunc2.getY(0), -0.1411200081, ACCURACY);
        assertEquals(listOfMathFunc3.getY(0), -0.5440211109, ACCURACY);

    }

    @Test
    public void testSetY() {
        listOfArray.setY(2, 39);
        assertEquals(listOfArray.getY(2), 39, ACCURACY);

    }

    @Test
    public void testIndexOfX() {
        assertEquals(listOfArray.indexOfX(3), 2);
        assertEquals(listOfMathFunc1.indexOfX(3), -1);
        assertEquals(listOfMathFunc1.indexOfX(100), -1);
    }

    @Test
    public void testIndexOfY() {
        assertEquals(listOfArray.indexOfY(4), 1);
        assertEquals(listOfArray.indexOfY(6), 2);
        assertEquals(listOfArray.indexOfY(8), 3);
    }

    @Test
    public void testFloorIndexOfX() {
        assertEquals(listOfArray.floorIndexOfX(4.5), 3);
        assertEquals(listOfArray.floorIndexOfX(3.2), 2);
        assertEquals(listOfMathFunc1.floorIndexOfX(1.1), 0);
        assertEquals(listOfMathFunc1.floorIndexOfX(100), 10);

    }

    @Test
    public void testExtrapolateLeft() {
        assertEquals(listOfMathFunc2.extrapolateLeft(-4), 0.810402615850074, ACCURACY);
        assertEquals(listOfMathFunc1.extrapolateLeft(0), 0.502717214644860, ACCURACY);
        assertEquals(listOfArray.extrapolateLeft(0), 0.0, ACCURACY);
    }

    @Test
    public void testExtrapolateRight() {
        assertEquals(listOfMathFunc1.extrapolateRight(6), -0.894118534015851, ACCURACY);
        assertEquals(listOfMathFunc1.extrapolateRight(7), -0.829312793368563, ACCURACY);
        assertEquals(listOfArray.extrapolateRight(6), 12, ACCURACY);
    }

    @Test
    public void testInterpolate() {
        assertEquals(listOfArray.interpolate(5, 4), 10.0);
        assertEquals(listOfArray.interpolate(4, 3), 8.0);
        assertEquals(listOfArray.interpolate(3, 2), 6.0);
    }

    @Test
    public void testFloorNodeOfX() {
    }

    @Test
    public void testApply() {
    }

    @Test
    public void testInsert() {
    }
}