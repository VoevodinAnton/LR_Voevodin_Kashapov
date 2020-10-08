package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MathFunctionTest {
    public static final double ACCURACY = 0.00000001;

    @Test
    public void testMathFunction() {
        MathFunction sq = new SqrFunction();
        MathFunction sine = new SinFunction();
        MathFunction tenth = new TenthPowFunction();
        assertEquals(tenth.andThen(sine).andThen(sq).apply(1),
                0.7080734182735712, ACCURACY);
        assertEquals(sine.andThen(tenth).andThen(sq).apply(2),
                0.149320433481448, ACCURACY);
        assertEquals(sq.andThen(sine).andThen(tenth).apply(5),
                1.649289461102213e-09, ACCURACY);
        assertEquals(sq.andThen(tenth).andThen(sine).apply(10),
                -0.645251285265781, ACCURACY);
    }
}