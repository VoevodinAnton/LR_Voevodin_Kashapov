package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AbstractTabulatedFunctionTest {
    private final static double ACCURACY = 0.00001;
    MockTabulatedFunction mock = new MockTabulatedFunction();

    @Test
    public void testInterpolate() {

        assertEquals(mock.interpolate(2.0, 1.0, 5.0, 4.0, 2.0), 3.5, ACCURACY);
        assertEquals(mock.interpolate(5.0, 3.0, 7.0, 6.0, 8.0), 7, ACCURACY);
        assertEquals(mock.interpolate(3.0, 2.71828182854, 3.1415926535, 3.1415926535, 2.71828182854), 2.859874482040000, ACCURACY);
        assertEquals(mock.interpolate(9.0, 2.71828182854*2.71828182854, 3.1415926535*3.1415926535, 3.1415926535, 2.71828182854), 2.866681662562116, ACCURACY);
        assertEquals(mock.interpolate(2.0, 1.0, 3.0, 10.0, 10.0), 10, ACCURACY);
    }

    @Test
    public void applyTest(){
    }
}
