package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SqrFunctionTest {
    @Test
    public void SqrFunctionAppTest() {
        SqrFunction x = new SqrFunction();

        assertEquals(x.apply(2), 4.0);

        assertEquals(x.apply(3), 9.0);

        assertEquals(x.apply(4), 16.0);

        assertEquals(x.apply(5), 25.0);

        assertEquals(x.apply(6), 36.0);
    }
}