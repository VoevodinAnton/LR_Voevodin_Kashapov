package operations;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.Point;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import static org.testng.Assert.*;

public class TabulatedFunctionOperationServiceTest {
    public static final double ACCURACY = 0.00001;

    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};


    @Test
    public void testAsPoints() {
        TabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);
        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        assertEquals(points[0].x, 1.0);
        assertEquals(points[1].x, 2.0);
        assertEquals(points[2].x, 3.0);
        assertEquals(points[3].x, 4.0);
        assertEquals(points[4].x, 5.0);
        assertEquals(points.length, 5);

    }
}