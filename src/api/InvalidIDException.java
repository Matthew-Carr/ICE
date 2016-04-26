package api;

public class InvalidIDException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidIDException(String message) {
        super(message);
    }

    public InvalidIDException(Throwable cause) {
        super(cause);
    }

}
