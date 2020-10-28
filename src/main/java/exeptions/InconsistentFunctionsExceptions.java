package exeptions;

public class InconsistentFunctionsExceptions extends RuntimeException {
    public InconsistentFunctionsExceptions() {
    }

    public InconsistentFunctionsExceptions(String message) {
        super(message);
    }
}
