package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions;

public class InterpolationException extends RuntimeException {
    private static final long serialVersionUID = 7053229860784225280L;

    public InterpolationException() {
    }

    public InterpolationException(String message) {
        super(message);
    }
}
