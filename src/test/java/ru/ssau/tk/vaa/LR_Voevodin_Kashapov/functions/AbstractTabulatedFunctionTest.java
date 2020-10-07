package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AbstractTabulatedFunctionTest {
    private final static double ACCURACY = 0.00001;
    MockTabulatedFunction mock = new MockTabulatedFunction();

    @Test
    public void testInterpolate() {
        assertEquals(mock.interpolate(2.0, 1.0, 5.0, 4.0, 2.0), 3.5, ACCURACY);
    }

    @Test
    public void applyTest(){
        assertEquals(mock.apply(5), 2.0, ACCURACY);
    }
}
