package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SinFunctionTest {
    @Test
    public void applyTest(){
        SinFunction function = new SinFunction();

        assertEquals(function.apply(0), 0.0);
        assertEquals(function.apply(Math.PI/6), 0.49999999999999994);
    }
}