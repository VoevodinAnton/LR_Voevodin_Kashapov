package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.CompositeFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.IdentityFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.SinFunction;

import static org.testng.Assert.*;

public class CompositeFunctionTest {
    @Test
    public void applyTest() {
        IdentityFunction functionID = new IdentityFunction();
        SinFunction functionSin = new SinFunction();
        CompositeFunction functionCom = new CompositeFunction(functionID, functionSin);

        assertEquals(functionCom.apply(0), 0.0);
        assertEquals(functionCom.apply(Math.PI / 6), 0.49999999999999994);
    }

}