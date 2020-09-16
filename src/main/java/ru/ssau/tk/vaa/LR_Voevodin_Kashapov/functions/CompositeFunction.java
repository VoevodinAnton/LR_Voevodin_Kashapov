package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

public class CompositeFunction implements MathFunction {

    private MathFunction firstFunction;
    private MathFunction secondFunction;

    CompositeFunction(MathFunction firstFunction, MathFunction secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double apply(double x) {
        return secondFunction.apply(firstFunction.apply(x));
    }
}
