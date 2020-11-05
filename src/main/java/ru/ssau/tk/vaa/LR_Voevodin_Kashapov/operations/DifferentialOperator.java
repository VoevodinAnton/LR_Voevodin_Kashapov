package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.MathFunction;

public interface DifferentialOperator<T extends MathFunction> {
    T derive(T function);

}
