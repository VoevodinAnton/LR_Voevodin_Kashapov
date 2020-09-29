package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    abstract protected int floorIndexOfX(double x);

    abstract protected double extrapolateLeft(double x);

    abstract protected double extrapolateRight(double x);

    abstract protected double interpolate(double x, int floorIndex);

    double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY - (rightY - leftY)/(rightX - leftX) * (x - leftX);
    }

    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        }
        else if (x > rightBound()) {
            return extrapolateRight(x);
        }
        else {
            if (indexOfX(x) == -1) {
                return interpolate(x, floorIndexOfX(x));
            }
            else {
                return GetY(indexOfX(x));
            }
        }
    }

}
