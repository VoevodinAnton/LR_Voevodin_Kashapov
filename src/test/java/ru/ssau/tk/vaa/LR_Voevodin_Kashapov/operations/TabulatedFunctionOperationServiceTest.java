package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.InconsistentFunctionsExceptions;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.factory.LinkedListTabulatedFunctionFactory;
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
    public void multiplyTest() {
        TabulatedFunction functionListTest = getList();
        TabulatedFunction functionArrayTest = getArray();


        TabulatedFunction MultiplyOfList = new TabulatedFunctionOperationService().multiply(functionListTest, functionListTest);
        int i = 0;
        for (Point point : MultiplyOfList) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] * yValues[i++]);
        }

        TabulatedFunction MultiplyOfArray = new TabulatedFunctionOperationService().multiply(functionArrayTest, functionArrayTest);
        i = 0;
        for (Point point : MultiplyOfArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] * yValuesArray[i++]);
        }

        TabulatedFunction MultiplyOfListAndArray = new TabulatedFunctionOperationService().multiply(functionArrayTest, functionListTest);
        i = 0;
        for (Point point : MultiplyOfListAndArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] * yValues[i++]);
        }
    }

    @Test
    public void divideTest() {
        TabulatedFunction functionListTest = getList();
        TabulatedFunction functionArrayTest = getArray();


        TabulatedFunction DivideOfList = new TabulatedFunctionOperationService().divide(functionListTest, functionListTest);
        int i = 0;
        for (Point point : DivideOfList) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] / yValues[i++]);
        }

        TabulatedFunction DivideOfArray = new TabulatedFunctionOperationService().divide(functionArrayTest, functionArrayTest);
        i = 0;
        for (Point point : DivideOfArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] / yValuesArray[i++]);
        }

        TabulatedFunction DivideOfListAndArray = new TabulatedFunctionOperationService().divide(functionArrayTest, functionListTest);
        i = 0;
        for (Point point : DivideOfListAndArray) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValuesArray[i] / yValues[i++]);
        }
    }
}