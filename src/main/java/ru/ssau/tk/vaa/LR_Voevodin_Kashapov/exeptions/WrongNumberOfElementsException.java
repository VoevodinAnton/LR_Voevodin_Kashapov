package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions;

public class WrongNumberOfElementsException extends RuntimeException {

    public WrongNumberOfElementsException() {
        super("Wrong number");
    }
}
