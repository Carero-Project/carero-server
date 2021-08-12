package com.carero.advice.exception;

public class SameBeforePasswordException extends RuntimeException{
    public SameBeforePasswordException() {
        super();
    }

    public SameBeforePasswordException(String message) {
        super(message);
    }
}
