package com.codigo.msticket.exception;

public class AsientoNotFoundException extends RuntimeException {
    public AsientoNotFoundException(String message) {
        super(message);
    }

    public AsientoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
