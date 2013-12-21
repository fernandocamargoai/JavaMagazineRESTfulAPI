package br.com.devmedia.javamagazine.restfulapi.rest.exception;

public class OnlyCommentsCreatorCanModifyException extends RuntimeException {

    public OnlyCommentsCreatorCanModifyException() {
    }

    public OnlyCommentsCreatorCanModifyException(String message) {
        super(message);
    }

    public OnlyCommentsCreatorCanModifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnlyCommentsCreatorCanModifyException(Throwable cause) {
        super(cause);
    }

    public OnlyCommentsCreatorCanModifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
