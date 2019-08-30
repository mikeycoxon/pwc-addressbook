package au.com.pwc.addressbook;

public class BookException extends RuntimeException {
    public BookException(String message, Throwable t) {
        super(message, t);
    }
}
