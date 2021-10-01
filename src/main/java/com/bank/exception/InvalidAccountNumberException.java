package com.bank.exception;

public class InvalidAccountNumberException extends Exception{
    private static final long serialVersionUID = 1L;

    public InvalidAccountNumberException(String message) {
        super(message);
    }
}
