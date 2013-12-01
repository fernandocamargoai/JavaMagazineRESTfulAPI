package br.com.devmedia.javamagazine.restfulapi.rest.exception;

public class OnlyPostsCreatorCanDeleteException extends RuntimeException {

    public OnlyPostsCreatorCanDeleteException() {
    }

    public OnlyPostsCreatorCanDeleteException(String message) {
        super(message);
    }

    public OnlyPostsCreatorCanDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnlyPostsCreatorCanDeleteException(Throwable cause) {
        super(cause);
    }

    public OnlyPostsCreatorCanDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
