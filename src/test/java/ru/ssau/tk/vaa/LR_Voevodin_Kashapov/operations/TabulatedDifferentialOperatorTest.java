package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ArrayTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import static org.testng.Assert.*;

public class TabulatedDifferentialOperatorTest {
    public static final double ACCURACY = 0.00001;

    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    @Test
    public void testDerive() {
        TabulatedFunction LinkedListTabulatedFunction = new LinkedListTabulatedFunction(xValues, yValues);
        TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction diffFunctionList = differentialOperator.derive(LinkedListTabulatedFunction);

        assertEquals(diffFunctionList.getX(0), 1, ACCURACY);
        assertEquals(diffFunctionList.getX(4), 5, ACCURACY);
        assertEquals(diffFunctionList.getY(0), 2, ACCURACY);
        assertEquals(diffFunctionList.getY(4), 2, ACCURACY);


        TabulatedFunction ArrayTabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedDifferentialOperator differentialOperator1 = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction diffFunctionArray = differentialOperator1.derive(ArrayTabulatedFunction);

        assertEquals(diffFunctionArray.getX(0), 1, ACCURACY);
        assertEquals(diffFunctionArray.getX(4), 5, ACCURACY);
        assertEquals(diffFunctionArray.getY(0), 2, ACCURACY);
        assertEquals(diffFunctionArray.getY(4), 2, ACCURACY);

    }
}