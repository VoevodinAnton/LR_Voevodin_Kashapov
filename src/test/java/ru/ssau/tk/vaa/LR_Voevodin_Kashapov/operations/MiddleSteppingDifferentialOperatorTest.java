package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.SqrFunction;

import static org.testng.Assert.*;

public class MiddleSteppingDifferentialOperatorTest {

    @Test
    public void testDerive() {
        double step = 0.001;
        SteppingDifferentialOperator differentialOperator = new MiddleSteppingDifferentialOperator(step);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(0), 0, 0.0001);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(1), 2, 0.0001);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(2), 4, 0.0001);
    }
}