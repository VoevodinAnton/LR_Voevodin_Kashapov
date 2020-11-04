package exeptions;

public class InconsistentFunctionsExceptions extends RuntimeException {
    private static final long serialVersionUID = -1221970400808747943L;

    public InconsistentFunctionsExceptions() {
    }

    public InconsistentFunctionsExceptions(String message) {
        super(message);
    }
}
