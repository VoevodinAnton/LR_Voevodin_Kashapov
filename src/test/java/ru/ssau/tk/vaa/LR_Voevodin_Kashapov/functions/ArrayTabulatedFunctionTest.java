package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.InterpolationException;
import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class ArrayTabulatedFunctionTest {

    public static final double ACCURACY = 0.000000001;

    private final MathFunction source = new TenthPowFunction();
    private final double xFrom = 1;
    private final double xTo = 10;
    private final int count = 256;

    private final double[] x = new double[]{1, 4, 9, 16};
    private final double[] y = new double[]{1, 16, 81, 256};

    private final double[] x1 = new double[]{10, 20};
    private final double[] y1 = new double[]{10, 10};

    private final double[] x2 = new double[]{1, 2, 3};
    private final double[] y2 = new double[]{1, 2, 1};

    private ArrayTabulatedFunction arr1() {
        return new ArrayTabulatedFunction(source, xFrom, xTo, count);
    }

    private ArrayTabulatedFunction arr2() {
        return new ArrayTabulatedFunction(x, y);
    }

    private ArrayTabulatedFunction arr3() {
        return new ArrayTabulatedFunction(x1, y1);
    }

    private ArrayTabulatedFunction arr4() {
        return new ArrayTabulatedFunction(x2, y2);
    }

    @Test
    public void testConstructor() {
        double[] aX = new double[]{234};
        double[] aY = new double[]{777};
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(aX, aY));
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(source, aX[0], aX[0], 1));
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(source, aY[0], aX[0], count));
    }

    @Test
    public void testGetCount() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();

        assertEquals(t11.getCount(),
                4);
        assertEquals(t12.getCount(),
                2);
        assertEquals(t13.getCount(),
                3);
        assertEquals(t21.getCount(), count);
    }

    @Test
    public void testGetX() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t13.getX(0),
                1.0);
        assertEquals(t13.getX(1),
                2.0);
        assertEquals(t13.getX(2),
                3.0);
        assertEquals(t11.getX(1),
                4.0);
        assertEquals(t12.getX(1),
                20.0);
        assertEquals(t11.getX(3),
                16.0);
        assertEquals(t21.getX(0),
                1.0);

        assertThrows(IndexOutOfBoundsException.class, () -> t11.getX(34));
        assertThrows(IndexOutOfBoundsException.class, () -> t12.getX(58));
        assertThrows(IndexOutOfBoundsException.class, () -> t13.getX(-1));
    }

    @Test
    public void testGetY() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t13.getY(0),
                1.0);
        assertEquals(t13.getY(1),
                2.0);
        assertEquals(t13.getY(2),
                1.0);
        assertEquals(t21.getY(0),
                1.0);

        assertThrows(IndexOutOfBoundsException.class, () -> t21.getY(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> t21.getY(582));
        assertThrows(IndexOutOfBoundsException.class, () -> t13.getY(-1));
    }

    @Test
    public void testSetY() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t13 = arr4();
        t13.setY(2, 21);
        assertEquals(t13.getY(2),
                21.0);
        t13.setY(1, 4);
        assertEquals(t13.getY(1),
                4.0);
        t21.setY(45, 133);
        assertEquals(t21.getY(45),
                133.0);
        t11.setY(1, 0);
        assertEquals(t11.getY(1),
                0.0);

        assertThrows(IndexOutOfBoundsException.class, () -> t11.getX(34));
        assertThrows(IndexOutOfBoundsException.class, () -> t21.getX(584));
        assertThrows(IndexOutOfBoundsException.class, () -> t13.getX(-1));
    }

    @Test
    public void testLeftBound() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t11.leftBound(),
                1.0);
        assertEquals(t12.leftBound(),
                10.0);
        assertEquals(t13.leftBound(),
                1.0);
        assertEquals(t21.leftBound(),
                1.0);
    }

    @Test
    public void testRightBound() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t11.rightBound(),
                16.0);
        assertEquals(t12.rightBound(),
                20.0);
        assertEquals(t13.rightBound(),
                3.0);
        assertEquals(t21.rightBound(),
                10.0);
    }

    @Test
    public void testIndexOfX() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t11.indexOfX(9),
                2);
        assertEquals(t12.indexOfX(20),
                1);
        assertEquals(t13.indexOfX(1),
                0);
        assertEquals(t21.indexOfX(10),
                255);
    }

    @Test
    public void testIndexOfY() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t11.indexOfY(256),
                3);
        assertEquals(t12.indexOfY(10),
                0);
        assertEquals(t13.indexOfY(2),
                1);
        assertEquals(t21.indexOfY(1),
                0);
    }

    @Test
    public void testFloorIndexOfX() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t11.floorIndexOfX(7),
                1);
        assertThrows(IllegalArgumentException.class, () -> t12.floorIndexOfX(4));
        assertThrows(IllegalArgumentException.class, () -> t12.floorIndexOfX(9));
        assertEquals(t13.floorIndexOfX(5),
                3);
    }

    @Test
    public void testInterpolate() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();

        assertEquals(t11.interpolate(3, 0), 11, ACCURACY);
        assertEquals(t12.interpolate(15, 0), 10, ACCURACY);

        assertThrows(InterpolationException.class, () -> t12.interpolate(15, 1));
        assertThrows(InterpolationException.class, () -> t21.interpolate(9, 34));


    }

    @Test
    public void testExtrapolateLeft() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t11.extrapolateLeft(0),
                -4.0);
        assertEquals(t12.extrapolateLeft(5),
                10.0);
        assertEquals(t13.extrapolateLeft(-100),
                -100.0);
        assertEquals(t21.extrapolateLeft(0),
                -10.747351676774247);
    }

    @Test
    public void testExtrapolateRight() {
        ArrayTabulatedFunction t21 = arr1();
        ArrayTabulatedFunction t11 = arr2();
        ArrayTabulatedFunction t12 = arr3();
        ArrayTabulatedFunction t13 = arr4();
        assertEquals(t11.extrapolateRight(20),
                356.0);
        assertEquals(t12.extrapolateRight(30),
                10.0);
        assertEquals(t13.extrapolateRight(1010),
                -1006.0);
        assertEquals(t21.extrapolateRight(15),
                5.921331043308460e+10);
    }

    @Test
    public void testDifficultFunc() {
        double xFrom = 5;
        double xTo = 10;
        int count = 64;
        MathFunction sqr = new SqrFunction();
        MathFunction sin = new SinFunction();
        MathFunction ten = new TenthPowFunction();
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(sin.andThen(sqr).andThen(ten), xFrom, xTo, count);
        LinkedListTabulatedFunction g = new LinkedListTabulatedFunction(sin.andThen(sqr).andThen(ten), xFrom, xTo, count);
        assertEquals(f.getY(0),
                g.getY(0), ACCURACY);
        assertEquals(f.getY(1),
                g.getY(1), ACCURACY);
        assertEquals(f.getY(2),
                g.getY(2), ACCURACY);
    }

    @Test
    public void testInsert() {
        ArrayTabulatedFunction t11 = arr2();
        // тестирование для одинаковых элементов
        t11.insert(4, 2);
        assertEquals(t11.getX(0), 1.0);
        assertEquals(t11.getY(0), 1.0);
        assertEquals(t11.getX(1), 4.0);
        assertEquals(t11.getY(1), 2.0);
        assertEquals(t11.getX(2), 9.0);
        assertEquals(t11.getY(2), 81.0);
        assertEquals(t11.getX(3), 16.0);
        assertEquals(t11.getY(3), 256.0);
        assertEquals(t11.getCount(), 4);
        // тестирование в середине массива
        t11.insert(5, 6);
        assertEquals(t11.getX(0), 1.0);
        assertEquals(t11.getY(0), 1.0);
        assertEquals(t11.getX(1), 4.0);
        assertEquals(t11.getY(1), 2.0);
        assertEquals(t11.getX(2), 5.0);
        assertEquals(t11.getY(2), 6.0);
        assertEquals(t11.getX(3), 9.0);
        assertEquals(t11.getY(3), 81.0);
        assertEquals(t11.getX(4), 16.0);
        assertEquals(t11.getY(4), 256.0);
        assertEquals(t11.getCount(), 5);

        ArrayTabulatedFunction t1 = arr2();
        t1.insert(0, 10);
        // тест для вставки в начало
        t11.insert(5, 6);
        assertEquals(t1.getX(0), 0.0);
        assertEquals(t1.getY(0), 10.0);
        assertEquals(t1.getX(1), 1.0);
        assertEquals(t1.getY(1), 1.0);
        assertEquals(t1.getX(2), 4.0);
        assertEquals(t1.getY(2), 16.0);
        assertEquals(t1.getX(3), 9.0);
        assertEquals(t1.getY(3), 81.0);
        assertEquals(t1.getX(4), 16.0);
        assertEquals(t1.getY(4), 256.0);
        assertEquals(t1.getCount(), 5);
        // тест для конца массива
        t1.insert(27, 1010);
        assertEquals(t1.getX(0), 0.0);
        assertEquals(t1.getY(0), 10.0);
        assertEquals(t1.getX(1), 1.0);
        assertEquals(t1.getY(1), 1.0);
        assertEquals(t1.getX(2), 4.0);
        assertEquals(t1.getY(2), 16.0);
        assertEquals(t1.getX(3), 9.0);
        assertEquals(t1.getY(3), 81.0);
        assertEquals(t1.getX(4), 16.0);
        assertEquals(t1.getY(4), 256.0);
        assertEquals(t1.getX(5), 27.0);
        assertEquals(t1.getY(5), 1010.0);
        assertEquals(t1.getCount(), 6);

    }

    @Test
    public void testRemove() {
        ArrayTabulatedFunction a = arr2();
        // тест для начала
        a.remove(0);
        assertEquals(a.getX(0), 4.0);
        assertEquals(a.getY(0), 16.0);
        assertEquals(a.getX(1), 9.0);
        assertEquals(a.getY(1), 81.0);
        assertEquals(a.getX(2), 16.0);
        assertEquals(a.getY(2), 256.0);
        assertEquals(a.getCount(), 3);
        // тест для середины
        assertEquals(a.getY(1), 81.0);
        a.remove(1);
        assertEquals(a.getX(0), 4.0);
        assertEquals(a.getY(0), 16.0);
        assertEquals(a.getX(1), 16.0);
        assertEquals(a.getY(1), 256.0);
        assertEquals(a.getCount(), 2);
        // тест для конца
        /*a.remove(1);
        assertEquals(a.getX(0), 4.0);
        assertEquals(a.getY(0), 16.0);
        assertEquals(a.getCount(), 1);*/
        assertThrows(IllegalArgumentException.class, () -> a.remove(0));
    }

    @Test
    public void testItWhile() {
        ArrayTabulatedFunction arr = arr2();
        Iterator<Point> it1 = arr.iterator();
        int i = 0;
        while (it1.hasNext()) {
            Point a = it1.next();
            assertEquals(arr.getX(i), a.x);
            assertEquals(arr.getY(i++), a.y);
        }
        assertEquals(arr.getCount(), i);
        assertThrows(NoSuchElementException.class, it1::next);
    }

    @Test
    public void testIteratorForEach() {
        ArrayTabulatedFunction arr0 = arr2();
        int i = 0;
        for (Point a : arr0) {
            assertEquals(a.x, arr0.getX(i));
            assertEquals(a.y, arr0.getY(i++));
        }
        assertEquals(arr0.getCount(), i);
    }
}