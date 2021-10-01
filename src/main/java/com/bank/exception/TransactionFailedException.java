package com.bank.exception;

public class TransactionFailedException extends Exception{

    public TransactionFailedException(String message) {
        super(message);
    }
}
