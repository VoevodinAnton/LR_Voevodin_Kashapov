package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

public class ConstantFunction implements MathFunction {
    private final double CONSTANT;

    public ConstantFunction(double CONSTANT) {
        this.CONSTANT = CONSTANT;
    }

    public double getCONSTANT() {
        return CONSTANT;
    }

    public double apply(double x) {
        return CONSTANT;
    }


}
