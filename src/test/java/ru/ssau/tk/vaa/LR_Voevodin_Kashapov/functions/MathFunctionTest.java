package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MathFunctionTest {
    public static final double ACCURACY = 0.000001;

    @Test
    public void testMathFunction() {
        MathFunction sq = new SqrFunction();
        MathFunction sine = new SinFunction();
        MathFunction tenth = new TenthPowFunction();
        assertEquals(tenth.andThen(sine).andThen(sq).apply(1), 0.7080734182735712, ACCURACY);
    }

}