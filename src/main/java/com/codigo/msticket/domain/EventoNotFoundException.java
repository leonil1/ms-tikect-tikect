package com.codigo.msticket.domain;

public class EventoNotFoundException extends RuntimeException {
    public EventoNotFoundException(String message) {
        super(message);
    }

    public EventoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}