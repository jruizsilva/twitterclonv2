package twitterclonv2.common.exception;

public class CustomObjectNotFoundException extends RuntimeException {

    public CustomObjectNotFoundException() {
        super();
    }

    public CustomObjectNotFoundException(String message) {
        super(message);
    }

    public CustomObjectNotFoundException(String message,
                                         Throwable cause) {
        super(message,
              cause);
    }
}

