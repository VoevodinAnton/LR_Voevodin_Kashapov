package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AbstractTabulatedFunction {
    MockTabulatedFunction mock = new MockTabulatedFunction();

    @Test
    public void testInterpolate() {
        assertEquals(mock.interpolate(2.0, 1.0, 5.0, 4.0, 2.0), 3.5);
    }
}
