package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.InconsistentFunctionsExceptions;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ArrayTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.Point;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TabulatedFunctionOperationServiceTest {
    public static final double ACCURACY = 0.00001;

    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};
    private final double[] yValuesArray = new double[]{4, 6, 8, 10, 12};

    private LinkedListTabulatedFunction getList() {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    private ArrayTabulatedFunction getArray() {
        return new ArrayTabulatedFunction(xValues, yValuesArray);
    }


    @Test
    public void testAsPoints() {
        TabulatedFunction functionListTest = getList();
        Point[] points = TabulatedFunctionOperationService.asPoints(functionListTest);
        int i = 0;
        for (Point point : points) {
            assertEquals(point.x, functionListTest.getX(i));
            assertEquals(point.y, functionListTest.getY(i++));
        }
    }

    @Test
    public void getFactoryTest() {
        assertTrue(new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory()).getFactory() instanceof LinkedListTabulatedFunctionFactory);
    }

    @Test
    public void exceptionsTest() {
        TabulatedFunction functionListTest = getList();

        double[] xValuesErr = new double[]{1, 2, 3};
        double[] yValuesErr = new double[]{2, 4, 6};
        var functionErr = new LinkedListTabulatedFunctionFactory().create(xValuesErr, yValuesErr);
        assertThrows(InconsistentFunctionsExceptions.class, () -> new TabulatedFunctionOperationService().multiply(functionErr, functionListTest));

        double[] xValuesErr1 = new double[]{1, 2, 3, 4, 6};
        double[] yValuesErr1 = new double[]{2, 4, 6, 8, 10};
        var functionErr1 = new ArrayTabulatedFunctionFactory().create(xValuesErr1, yValuesErr1);
        assertThrows(InconsistentFunctionsExceptions.class, () -> new TabulatedFunctionOperationService().divide(functionListTest, functionErr1));


    }

    @Test
    public void testAdd() {
        TabulatedFunction functionListTest = getList();
        TabulatedFunction functionArrayTest = getArray();


        TabulatedFunction addOfList = new TabulatedFunctionOperationService().add(functionListTest, functionListTest);
        int i = 0;
        for (Point point : addOfList) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] + yValues[i++]);
        }
        assertTrue(addOfList instanceof ArrayTabulatedFunction);

        TabulatedFunction addOfArray = new TabulatedFunctionOperationService().add(functionArrayTest, functionArrayTest);
        i = 0;
        for (Point point : addOfArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] + yValuesArray[i++]);
        }
        assertTrue(addOfArray instanceof ArrayTabulatedFunction);

        TabulatedFunction addOfListAndArray = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory()).add(functionArrayTest, functionListTest);
        i = 0;
        for (Point point : addOfListAndArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] + yValues[i++]);
        }
        assertTrue(addOfListAndArray instanceof LinkedListTabulatedFunction);

    }

    @Test
    public void testSubtract() {
        TabulatedFunction functionListTest = getList();
        TabulatedFunction functionArrayTest = getArray();


        TabulatedFunction subtractOfList = new TabulatedFunctionOperationService().subtract(functionListTest, functionListTest);
        int i = 0;
        for (Point point : subtractOfList) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] - yValues[i++]);
        }
        assertTrue(subtractOfList instanceof ArrayTabulatedFunction);

        TabulatedFunction subtractOfArray = new TabulatedFunctionOperationService().subtract(functionArrayTest, functionArrayTest);
        i = 0;
        for (Point point : subtractOfArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] - yValuesArray[i++]);
        }
        assertTrue(subtractOfArray instanceof ArrayTabulatedFunction);

        TabulatedFunction subtractOfListAndArray = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory()).subtract(functionArrayTest, functionListTest);
        i = 0;
        for (Point point : subtractOfListAndArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] - yValues[i++]);
        }

        assertTrue(subtractOfListAndArray instanceof LinkedListTabulatedFunction);
    }

    @Test
    public void multiplyTest() {
        TabulatedFunction functionListTest = getList();
        TabulatedFunction functionArrayTest = getArray();


        TabulatedFunction multiplyOfList = new TabulatedFunctionOperationService().multiply(functionListTest, functionListTest);
        int i = 0;
        for (Point point : multiplyOfList) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] * yValues[i++]);
        }
        assertTrue(multiplyOfList instanceof ArrayTabulatedFunction);

        TabulatedFunction multiplyOfArray = new TabulatedFunctionOperationService().multiply(functionArrayTest, functionArrayTest);
        i = 0;
        for (Point point : multiplyOfArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] * yValuesArray[i++]);
        }
        assertTrue(multiplyOfArray instanceof ArrayTabulatedFunction);

        TabulatedFunction multiplyOfListAndArray = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory()).multiply(functionArrayTest, functionListTest);
        i = 0;
        for (Point point : multiplyOfListAndArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] * yValues[i++]);
        }
        assertTrue(multiplyOfListAndArray instanceof LinkedListTabulatedFunction);
    }

    @Test
    public void divideTest() {
        TabulatedFunction functionListTest = getList();
        TabulatedFunction functionArrayTest = getArray();


        TabulatedFunction divideOfList = new TabulatedFunctionOperationService().divide(functionListTest, functionListTest);
        int i = 0;
        for (Point point : divideOfList) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] / yValues[i++]);
        }
        assertTrue(divideOfList instanceof ArrayTabulatedFunction);

        TabulatedFunction divideOfArray = new TabulatedFunctionOperationService().divide(functionArrayTest, functionArrayTest);
        i = 0;
        for (Point point : divideOfArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] / yValuesArray[i++]);
        }
        assertTrue(divideOfArray instanceof ArrayTabulatedFunction);

        TabulatedFunction divideOfListAndArray = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory()).divide(functionArrayTest, functionListTest);
        i = 0;
        for (Point point : divideOfListAndArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] / yValues[i++]);
        }
        assertTrue(divideOfListAndArray instanceof LinkedListTabulatedFunction);
    }

}