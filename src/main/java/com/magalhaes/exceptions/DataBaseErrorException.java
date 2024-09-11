package com.magalhaes.exceptions;

public class DataBaseErrorException extends RuntimeException{
    private String code;

    public DataBaseErrorException(String message, String code) {
        super(message + " Code: " + code);
    }
}
