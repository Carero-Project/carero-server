package com.carero.advice.exception;

public class SessionTimeoutException extends RuntimeException{
    public SessionTimeoutException() {
        super();
    }

    public SessionTimeoutException(String message) {
        super(message);
    }
}
