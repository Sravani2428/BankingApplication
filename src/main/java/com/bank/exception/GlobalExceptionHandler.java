package com.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends Exception{

    private static final long serialVersionUID = 1L;

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> customerNotFoundException(CustomerNotFoundException e,
                                                                   WebRequest request) {

        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAccountNumberException.class)
    public ResponseEntity<ErrorResponse> invalidAccountNumberException(InvalidAccountNumberException e,
                                                                   WebRequest request) {

        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorResponse> invalidDepositAmountException(InvalidAmountException e,
                                                                       WebRequest request) {

        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> insufficientFundsException(InsufficientFundsException e,
                                                                        WebRequest request) {

        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(PrioritizeCustomersNotFoundException.class)
    public ResponseEntity<ErrorResponse> prioritizeCustomersNotFound(PrioritizeCustomersNotFoundException e,
                                                                     WebRequest request) {

        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionsNotFoundException.class)
    public ResponseEntity<ErrorResponse> transactionsNotFoundException(TransactionsNotFoundException e,
                                                                    WebRequest request) {

        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<ErrorResponse> transactionFailedException(TransactionFailedException e,
                                                                       WebRequest request) {

        ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
    }
}
