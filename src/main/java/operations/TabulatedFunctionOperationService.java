package operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.Point;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

public class TabulatedFunctionOperationService {

    public static Point[] asPoints(TabulatedFunction tabulatedFunction){
        int i = 0;
        Point[] points = new Point[tabulatedFunction.getCount()];

        for (Point point: tabulatedFunction){
            points[i++] = point;
        }
        return points;
    }
}
