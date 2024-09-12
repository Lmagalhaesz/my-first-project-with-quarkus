package com.magalhaes.exceptions;

public class DirectoryErrorException extends RuntimeException{
    private String code;

    public DirectoryErrorException(String message, String code) {
        super(message + " Code: " + code);
    }
}
