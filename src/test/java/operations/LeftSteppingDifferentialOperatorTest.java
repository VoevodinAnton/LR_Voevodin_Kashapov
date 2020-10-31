package operations;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.SinFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.SqrFunction;

import static org.testng.Assert.*;

public class LeftSteppingDifferentialOperatorTest {
    @Test
    public void testDerive() {
        double step = 0.01;
        SteppingDifferentialOperator differentialOperator = new LeftSteppingDifferentialOperator(step);
        assertEquals(differentialOperator.derive(new SinFunction()).apply(0), 0.9999, 0.0001);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(2), 3.9899, 0.0001);
        assertEquals(differentialOperator.derive(new SinFunction()).apply(1), 0.5445, 0.0001);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(4), 7.99, 0.0001);
    }
}