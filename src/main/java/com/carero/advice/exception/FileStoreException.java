package com.carero.advice.exception;

public class FileStoreException extends RuntimeException {
    public FileStoreException() {
        super();
    }

    public FileStoreException(String message) {
        super(message);
    }
}
