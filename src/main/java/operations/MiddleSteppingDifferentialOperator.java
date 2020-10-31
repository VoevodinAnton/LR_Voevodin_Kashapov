package operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.MathFunction;

public class MiddleSteppingDifferentialOperator extends SteppingDifferentialOperator {

    public MiddleSteppingDifferentialOperator(double step) {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction func) {
        return x -> (func.apply(x + step) - func.apply(x - step)) / (2 * step);
    }
}
