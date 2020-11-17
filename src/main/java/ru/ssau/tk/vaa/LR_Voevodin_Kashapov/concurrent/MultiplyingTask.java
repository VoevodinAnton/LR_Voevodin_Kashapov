package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.concurrent;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

public class MultiplyingTask implements Runnable {
    TabulatedFunction function;

    public MultiplyingTask(TabulatedFunction func) {
        this.function = func;
    }

    @Override
    public void run() {
        double x;
        double y;
        for (int i = 0; i < function.getCount(); i++) {
            x = function.getX(i);
            y = function.getY(i);
            System.out.printf("%s, i = %d, x = %f, old y = %f \n", Thread.currentThread().getName(), i, x, y);
            function.setY(i, y * 10);
            y = function.getY(i);
            System.out.printf("%s, i = %d, x = %f, new y = %f \n", Thread.currentThread().getName(), i, x, y);
        }
    }

}
