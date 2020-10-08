package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArrayTabulatedFunctionTest {
    MathFunction Source = new TenthPowFunction();
    double xFrom = 1;
    double xTo = 10;
    int count = 256;
    AbstractTabulatedFunction T21 = new ArrayTabulatedFunction(Source, xFrom, xTo, count);

    double[] x = new double[]{1, 4, 9, 16};
    double[] y = new double[]{1, 16, 81, 256};
    AbstractTabulatedFunction T11 = new ArrayTabulatedFunction(x, y);

    double[] x1 = new double[]{10, 20};
    double[] y1 = new double[]{10, 10};
    AbstractTabulatedFunction T12 = new ArrayTabulatedFunction(x1, y1);

    double[] x2 = new double[]{1, 2, 3};
    double[] y2 = new double[]{1, 2, 1};
    AbstractTabulatedFunction T13 = new ArrayTabulatedFunction(x2, y2);

    @Test
    public void testGetCount() {

        assertEquals(T11.getCount(),
                4);

        assertEquals(T12.getCount(),
                2);

        assertEquals(T13.getCount(),
                3);

        assertEquals(T21.getCount(), count);
    }

    @Test
    public void testGetX() {
        assertEquals(T13.getX(0),
                1.0);
        assertEquals(T13.getX(1),
                2.0);
        assertEquals(T13.getX(2),
                3.0);
        assertEquals(T11.getX(1),
                4.0);
        assertEquals(T12.getX(1),
                20.0);
        assertEquals(T11.getX(3),
                16.0);
    }

    @Test
    public void testGetY() {
        assertEquals(T13.getY(0),
                1.0);
        assertEquals(T13.getY(1),
                2.0);
        assertEquals(T13.getY(2),
                1.0);

    }

    @Test
    public void testSetY() {
        T13.setY(2, 21);
        assertEquals(T13.getY(2),
                21.0);

        T13.setY(1, 4);
        assertEquals(T13.getY(1),
                4.0);

        T21.setY(45, 133);
        assertEquals(T21.getY(45),
                133.0);

        T11.setY(1, 0);
        assertEquals(T11.getY(1),
                0.0);
    }

    @Test
    public void testLeftBound() {
        assertEquals(T11.leftBound(),
                1.0);

        assertEquals(T12.leftBound(),
                10.0);

        assertEquals(T13.leftBound(),
                1.0);
        assertEquals(T21.leftBound(),
                1.0);
    }

    @Test
    public void testRightBound() {
        assertEquals(T11.rightBound(),
                16.0);

        assertEquals(T12.rightBound(),
                20.0);

        assertEquals(T13.rightBound(),
                3.0);
        assertEquals(T21.rightBound(),
                10.0);
    }

    @Test
    public void testIndexOfX() {
        assertEquals(T11.indexOfX(9),
                2);

        assertEquals(T12.indexOfX(20),
                1);

        assertEquals(T13.indexOfX(1),
                0);

        assertEquals(T21.indexOfX(10),
                255);
    }

    @Test
    public void testIndexOfY() {
        assertEquals(T11.indexOfY(256),
                3);

        assertEquals(T12.indexOfY(10),
                0);

        assertEquals(T13.indexOfY(2),
                1);

        assertEquals(T21.indexOfY(1),
                0);
    }

    @Test
    public void testFloorIndexOfX() {
        assertEquals(T11.floorIndexOfX(7),
                1);

        assertEquals(T12.floorIndexOfX(4),
                0);

        assertEquals(T13.floorIndexOfX(5),
                3);
    }

    @Test
    public void testInterpolate() {
        assertEquals(T11.interpolate(10, 3),
                106.0);

        assertEquals(T12.interpolate(15, 1),
                10.0);

        assertEquals(T13.interpolate(2.5, 2),
                1.5);

        assertEquals(T21.interpolate(1.32, 10),
                16.09399414199557);
    }

    @Test
    public void testExtrapolateLeft() {
        assertEquals(T11.extrapolateLeft(0),
                -4.0);

        assertEquals(T12.extrapolateLeft(5),
                10.0);

        assertEquals(T13.extrapolateLeft(-100),
                -100.0);

        assertEquals(T21.extrapolateLeft(0),
                -10.747351676774247);
    }

    @Test
    public void testExtrapolateRight() {

        assertEquals(T11.extrapolateRight(20),
                356.0);

        assertEquals(T12.extrapolateRight(30),
                10.0);

        assertEquals(T13.extrapolateRight(1010),
                -1006.0);

        assertEquals(T21.extrapolateRight(15),
                5.921331043308460e+10);
    }
}