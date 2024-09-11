package com.magalhaes.exceptions;

public class DocumentErrorException extends RuntimeException{
    private String code;

    public DocumentErrorException(String message, String code) {
        super(message + " Code: " + code);
    }
}
