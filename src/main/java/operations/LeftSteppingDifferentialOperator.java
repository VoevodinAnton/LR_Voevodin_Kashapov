package operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.MathFunction;

public class LeftSteppingDifferentialOperator extends SteppingDifferentialOperator {

    public LeftSteppingDifferentialOperator(double step) {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction func) {
        return x -> (func.apply(x) - func.apply(x - step)) / step;
    }
}
