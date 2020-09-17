package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UnitFunctionTest {
    @Test
    public void TestUnit() {

        UnitFunction u = new UnitFunction();

        assertEquals(u.apply(10), 1.0);

        assertEquals(u.apply(0), 1.0);

        assertEquals(u.apply(-10), 1.0);

    }

}