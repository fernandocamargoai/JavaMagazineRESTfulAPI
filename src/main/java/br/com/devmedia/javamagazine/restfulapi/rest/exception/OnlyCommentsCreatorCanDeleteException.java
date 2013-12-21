package br.com.devmedia.javamagazine.restfulapi.rest.exception;

public class OnlyCommentsCreatorCanDeleteException extends RuntimeException {

    public OnlyCommentsCreatorCanDeleteException() {
    }

    public OnlyCommentsCreatorCanDeleteException(String message) {
        super(message);
    }

    public OnlyCommentsCreatorCanDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnlyCommentsCreatorCanDeleteException(Throwable cause) {
        super(cause);
    }

    public OnlyCommentsCreatorCanDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
