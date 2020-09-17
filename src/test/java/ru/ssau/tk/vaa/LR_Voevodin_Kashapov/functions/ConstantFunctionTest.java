package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ConstantFunctionTest {
    @Test
    public void applyTest() {
        ConstantFunction c = new ConstantFunction(1);

        assertEquals(c.apply(2), 1.0);

        assertEquals(c.apply(Double.NEGATIVE_INFINITY), 1.0);

        assertEquals(c.apply(0), 1.0);

        assertEquals(c.apply(5), 1.0);


    }

}