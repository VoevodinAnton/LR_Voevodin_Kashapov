package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.IdentityFunction;

import static org.testng.Assert.*;

public class IdentityFunctionTest {
    @Test
    public void applyTest() {
        IdentityFunction function = new IdentityFunction();

        assertEquals(function.apply(4), 4.0);
        assertEquals(function.apply(0), 0.0);
        assertEquals(function.apply(-10), -10.0);
        assertEquals(function.apply(1 / 10.0), 0.1);
    }
}