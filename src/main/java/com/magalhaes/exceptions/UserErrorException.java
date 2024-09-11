package com.magalhaes.exceptions;

public class UserErrorException extends RuntimeException{
    private String code;

    public UserErrorException(String message, String code) {
        super(message + " Code: " + code);
    }
}
