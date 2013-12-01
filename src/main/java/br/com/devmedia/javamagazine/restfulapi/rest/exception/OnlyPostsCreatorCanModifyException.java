package br.com.devmedia.javamagazine.restfulapi.rest.exception;

public class OnlyPostsCreatorCanModifyException extends RuntimeException {

    public OnlyPostsCreatorCanModifyException() {
    }

    public OnlyPostsCreatorCanModifyException(String message) {
        super(message);
    }

    public OnlyPostsCreatorCanModifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnlyPostsCreatorCanModifyException(Throwable cause) {
        super(cause);
    }

    public OnlyPostsCreatorCanModifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
