package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

public interface TabulatedFunction extends MathFunction {
    int GetCount();

    double GetX(int index);

    double GetY(int index);

    void SetY(int index, double value);

    int indexOfX(double x);

    int indexOfY(double y);

    double leftBound();

    double rightBound();
}
