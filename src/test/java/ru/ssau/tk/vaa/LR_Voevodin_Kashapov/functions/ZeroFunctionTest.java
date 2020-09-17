package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ZeroFunctionTest {
    @Test
    public void ZeroTest() {

        ZeroFunction z = new ZeroFunction();

        assertEquals(z.apply(112), 0.0);

        assertEquals(z.apply(0), 0.0);

        assertEquals(z.apply(110000), 0.0);
    }

}