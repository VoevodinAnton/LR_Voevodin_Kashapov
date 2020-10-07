package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

public class MockTabulatedFunction extends AbstractTabulatedFunction {
    final double x0 = 1.0;
    final double x1 = 5.0;
    final double y0 = 4.0;
    final double y1 = 2.0;

    protected int floorIndexOfX(double x) {
        if (x < x0) {
            return 0;

        }
        else if (x >= x0 && x <= x1)  {
            return 1;
        }
        else {
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
        return 0;
    }

    @Override
    public double getY(int index) {
        return 0;
    }

    @Override
    public void setY(int index, double value) {

    }

    @Override
    public int indexOfX(double x) {
        return 0;
    }

    @Override
    public int indexOfY(double y) {
        return 0;
    }

    @Override
    public double leftBound() {
        return x0;
    }

    @Override
    public double rightBound() {
        return x1;
    }

}
