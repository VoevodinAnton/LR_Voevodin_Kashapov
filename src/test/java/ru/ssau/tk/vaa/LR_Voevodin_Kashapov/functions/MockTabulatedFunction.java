package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.AbstractTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.Point;

import java.util.Iterator;

public class MockTabulatedFunction extends AbstractTabulatedFunction {
    final double x0 = 1.0;
    final double x1 = 5.0;
    final double y0 = 4.0;
    final double y1 = 2.0;

    protected int floorIndexOfX(double x) {
        if (x < x0) {
            return 0;

        } else if (x >= x0 && x <= x1) {
            return 1;
        } else {
            return 2;
        }
    }

    protected double extrapolateLeft(double x) {
        return interpolate(x, x0, x1, y0, y1);
    }

    protected double extrapolateRight(double x) {
        return interpolate(x, x0, x1, y0, y1);
    }

    protected double interpolate(double x, int floorIndex) {
        return interpolate(x, x0, x1, y0, y1);
    }

    public int getCount() {
        return 2;
    }

    @Override
    public double getX(int index) {
        if (index == 1) {
            return x1;
        }
        return x0;
    }

    @Override
    public double getY(int index) {
        if (index == 1) {
            return y1;
        }
        return y0;
    }

    @Override
    public void setY(int index, double value) {
    }

    @Override
    public int indexOfX(double x) {
        if (x == x1) {
            return 1;
        }
        if (x == x0) {
            return 0;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        if (y == y1) {
            return 1;
        }
        if (y == y0) {
            return 0;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return x0;
    }

    @Override
    public double rightBound() {
        return x1;
    }

    @Override
    public Iterator<Point> iterator() {
        return null;
    }
}
