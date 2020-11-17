package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.concurrent;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

public class ReadWriteTask implements Runnable {
    TabulatedFunction function;
    Runnable postRunAction;

    public ReadWriteTask(TabulatedFunction function) {
        this.function = function;
    }

    public ReadWriteTask(TabulatedFunction function, Runnable postRunAction) {
        this.function = function;
        this.postRunAction = postRunAction;
    }

    @Override
    public void run() {
        double x;
        double y;
        for (int i = 0; i < function.getCount(); i++) {
            x = function.getX(i);
            synchronized (function) {
                y = function.getY(i);
                System.out.printf("%s, before write: i = %d, x = %f, y = %f \n", Thread.currentThread().getName(), i, x, y);
                function.setY(i, y + 1);
                y = function.getY(i);
            }
            System.out.printf("%s, after write: i = %d, x = %f, y = %f \n", Thread.currentThread().getName(), i, x, y);
        }
        postRunAction.run();
    }
}
