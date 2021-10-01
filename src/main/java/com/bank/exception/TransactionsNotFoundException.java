package com.bank.exception;

public class TransactionsNotFoundException extends Exception{
    public TransactionsNotFoundException(String message) {
        super(message);
    }
}
