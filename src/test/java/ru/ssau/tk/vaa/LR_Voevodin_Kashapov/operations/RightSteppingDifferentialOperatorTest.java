package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.SqrFunction;

import static org.testng.Assert.*;

public class RightSteppingDifferentialOperatorTest {

    @Test
    public void testDerive() {
        double step = 0.005;
        SteppingDifferentialOperator differentialOperator = new RightSteppingDifferentialOperator(step);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(0), 0.005, 0.0001);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(1), 2.005, 0.0001);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(2), 4.005, 0.0001);
    }
}