package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

public interface MathFunction {
    double apply(double x);

    default CompositeFunction andThen(MathFunction afterFunction) {
        return new CompositeFunction(this, afterFunction);
    }
}
