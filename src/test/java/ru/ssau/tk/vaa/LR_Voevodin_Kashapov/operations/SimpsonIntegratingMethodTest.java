package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;

import static org.testng.Assert.*;

public class SimpsonIntegratingMethodTest {
    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};
    private final MathFunction source1 = new TenthPowFunction();
    private final MathFunction source2 = new SinFunction();
    private LinkedListTabulatedFunction getList() {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    private ArrayTabulatedFunction getArray() {
        return new ArrayTabulatedFunction(xValues, yValues);
    }
    private ArrayTabulatedFunction getNiceTen() {
        int count = 256;
        double xFrom = 1;
        double xTo = 10;
        return new ArrayTabulatedFunction(source1, xFrom, xTo, count);
    }
    private ArrayTabulatedFunction getNiceSin() {
        int count = 128;
        double xFrom = 0;
        double xTo = 8;
        return new ArrayTabulatedFunction(source2, xFrom, xTo, count);
    }
    @Test
    public void testIntegrate() {
        SimpsonIntegratingMethod sum = new SimpsonIntegratingMethod();
        assertEquals(sum.integrate(getArray(), 1, 5), 24.0, 0.1);
        assertEquals(sum.integrate(getList(), 1, 5), 24.0, 0.1);
        assertEquals(sum.integrate(getNiceTen(), 1, 5), 4438920.36, 5000);
        assertEquals(sum.integrate(getNiceSin(), 0, 6.2831853), 0, 0.1);
    }
}