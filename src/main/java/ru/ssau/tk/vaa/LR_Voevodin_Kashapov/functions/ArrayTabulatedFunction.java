package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import java.util.Arrays;

public class ArrayTabulatedFunction {
    private double[] xValues;
    private double[] yValues;
    private int count;

    ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        this.count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
    }

    ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        double step = (xTo - xFrom) / (count - 1);
        this.count = count;

        xValues = new double[count];
        yValues = new double[count];
        for (int i = 0; i < count; i++) {
            this.xValues[i] = xFrom + i*step;
            this.yValues[i] = source.apply(xFrom + i*step);
        }
    }

    public int getCount() {
        return count;
    }

    public double[] getX() {
        return xValues;
    }

    public double[] getY() {
        return yValues;
    }

    public void setY(int index, double value) {
        this.yValues[index] = value;
    }

    public double leftBound() {
        return xValues[0];
    }

    public double rightBound() {
        return xValues[count];
    }

    public int indexOfX(double xValue) {
        for (int index = 0; index < count; index++) {
            if (xValues[index] == xValue) {
                return index;
            }
        }
        return -1;
    }

    public int indexOfY(double yValue) {
        for (int index = 0; index < count; index++) {
            if (yValues[index] == yValue) {
                return index;
            }
        }
        return -1;
    }

    public int floorIndexOfX(double xValue) {
        if (xValue < xValues[0]) {
            return 0;
        }
        for (int index = 1; index < count; index++) {
            if (xValue < xValues[index]) {
                return index - 1;
            }
        }
        return count;
    }
}
