package com.codigo.msticket.exception;

public class BuscarAsientoException extends RuntimeException {
    public BuscarAsientoException(String message) {
        super(message);
    }

    public BuscarAsientoException(String message, Throwable cause) {
        super(message, cause);
    }
}
