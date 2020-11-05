package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.ArrayIsNotSortedException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.DifferentLengthOfArraysException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.*;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    abstract protected int floorIndexOfX(double x);

    abstract protected double extrapolateLeft(double x);

    abstract protected double extrapolateRight(double x);

    abstract protected double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) / (rightX - leftX) * (x - leftX);
    }

    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        }
        if (x > rightBound()) {
            return extrapolateRight(x);
        }
        if (indexOfX(x) == -1) {
            return interpolate(x, floorIndexOfX(x));
        }
        return getY(indexOfX(x));
    }

    static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new DifferentLengthOfArraysException("Lengths of xValues and yValues are different");
        }
    }

    static void checkSorted(double[] xValues) {
        for (int i = 0; i < xValues.length - 1; i++) {
            if (xValues[i + 1] < xValues[i]) {
                throw new ArrayIsNotSortedException("xValues is not sorted");
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.getClass().getSimpleName()).append(" size = ").append(this.getCount()).append("\n");

        for (Point point : this) {
            str.append("[")
                    .append(point.x)
                    .append("; ")
                    .append(point.y)
                    .append("]\n");

        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }
}

