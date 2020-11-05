package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.InterpolationException;


public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable, Serializable {
    private static final long serialVersionUID = -6675414219615562609L;
    private double[] xValues;
    private double[] yValues;
    private int count;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues.length < 2) {
            throw new IllegalArgumentException("length less than 2 points");
        }
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        this.count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Length less than 2 points");
        }
        if (xFrom >= xTo) {
            throw new IllegalArgumentException("Incorrect bounds");
        }
        double step = (xTo - xFrom) / (count - 1);
        this.count = count;

        xValues = new double[count];
        yValues = new double[count];
        for (int i = 0; i < count; i++) {
            this.xValues[i] = xFrom + i * step;
            this.yValues[i] = source.apply(xFrom + i * step);
        }
    }

    public int getCount() {
        return count;
    }

    public double getX(int index) {
        checkIndex(index);
        return xValues[index];
    }

    public double getY(int index) {
        checkIndex(index);
        return yValues[index];
    }

    public void setY(int index, double value) {
        checkIndex(index);
        this.yValues[index] = value;
    }

    public double leftBound() {
        return xValues[0];
    }

    public double rightBound() {
        return xValues[count - 1];
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
            throw new IllegalArgumentException("X is less than the left border");
        }
        for (int index = 1; index < count; index++) {
            if (xValue < xValues[index]) {
                return index - 1;
            }
        }
        return count;
    }

    public double interpolate(double x, int floorIndex) {
        if (x < xValues[floorIndex] || x > xValues[floorIndex + 1]) {
            throw new InterpolationException("x is not in interval");
        }
        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    public double extrapolateLeft(double x) {
        if (count == 1) {
            return yValues[count - 1];
        }
        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    public double extrapolateRight(double x) {
        if (count == 1) {
            return yValues[count - 1];
        }
        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    public void insert(double x, double y) {
        int ch = 0;
        for (int u = 0; u < count; u++) {
            if (xValues[u] == x) {
                yValues[u] = y;
                ch = 1;
                break;
            }
        }
        if (ch == 0) {
            double[] yValues1 = new double[count + 1];
            double[] xValues1 = new double[count + 1];
            for (int u = 0; u < count; u++) {
                if (xValues[u] > x) {
                    System.arraycopy(yValues, 0, yValues1, 0, u);
                    yValues1[u] = y;
                    System.arraycopy(yValues, u, yValues1, u + 1, count - u);

                    System.arraycopy(xValues, 0, xValues1, 0, u);
                    xValues1[u] = x;
                    System.arraycopy(xValues, u, xValues1, u + 1, count - u);
                    break;
                }
            }
            if (x > xValues[count - 1]) {
                System.arraycopy(yValues, 0, yValues1, 0, count);
                yValues1[count] = y;
                System.arraycopy(xValues, 0, xValues1, 0, count);
                xValues1[count] = x;
            }
            count++;
            this.yValues = yValues1;
            this.xValues = xValues1;
        }
    }

    public void remove(int index) {
        double[] xValues1 = new double[count - 1];
        double[] yValues1 = new double[count - 1];
        System.arraycopy(yValues, 0, yValues1, 0, index);
        System.arraycopy(yValues, index + 1, yValues1, index, count - index - 1);

        System.arraycopy(xValues, 0, xValues1, 0, index);
        System.arraycopy(xValues, index + 1, xValues1, index, count - index - 1);
        count--;
        this.xValues = xValues1;
        this.yValues = yValues1;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > count - 1) {
            throw new IndexOutOfBoundsException("The index is out of bounds");
        }
    }

    public Iterator<Point> iterator() {
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Point point = new Point(xValues[i], yValues[i]);
                i++;
                return point;
            }
        };
    }
}
