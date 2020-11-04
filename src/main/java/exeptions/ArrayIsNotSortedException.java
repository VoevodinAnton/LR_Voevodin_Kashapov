package exeptions;

public class ArrayIsNotSortedException extends RuntimeException {
    private static final long serialVersionUID = -7888573054346810362L;

    public ArrayIsNotSortedException() {
    }

    public ArrayIsNotSortedException(String message) {
        super(message);
    }
}
