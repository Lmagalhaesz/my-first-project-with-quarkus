package com.magalhaes.exceptions;

public class GenericException extends RuntimeException{
    private String code;

    public GenericException(String message, String code) {
        super(message + " Code: " + code);
    }
}
