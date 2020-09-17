package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

public class ConstantFunction {
    final private double EXPONENTA;

    public ConstantFunction(double EXPONENTA) {
        this.EXPONENTA = EXPONENTA;
    }

    public double getEXPONENTA() {
        return EXPONENTA;
    }

    public double apply(double x) {
        return EXPONENTA;
    }



}
