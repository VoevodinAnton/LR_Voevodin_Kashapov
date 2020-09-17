package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TenthPowFunctionTest {
@Test
    public void TenthPowFunctionAppTest() {
        TenthPowFunction x = new TenthPowFunction();

        assertEquals(x.apply(2), 1024.0);

        assertEquals(x.apply(0), 0.0);
    }

}