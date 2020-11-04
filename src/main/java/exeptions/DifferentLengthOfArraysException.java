package exeptions;

public class DifferentLengthOfArraysException extends RuntimeException {
    private static final long serialVersionUID = -4334068450769017008L;

    public DifferentLengthOfArraysException() {
    }

    public DifferentLengthOfArraysException(String message) {
        super(message);
    }
}
