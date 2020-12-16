package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

public interface TabulatedFunction extends MathFunction, Iterable<Point> {
    int getCount();

    double getX(int index);

    double getY(int index);

    void setY(int index, double value);

    int indexOfX(double x);

    int indexOfY(double y);

    double leftBound();

    double rightBound();

    default boolean similar(TabulatedFunction function) {
        if (function.getCount() != this.getCount()) {
            return false;
        } else if (function.getCount() == this.getCount()) {
            for (int i = 0; i < function.getCount(); i++) {
                if (this.getX(i) != function.getX(i)) {
                    return false;
                }
            }
        }
        return true;
    }

}
