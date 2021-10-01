package com.bank.service;

import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAccountNumberException;
import com.bank.exception.InvalidAmountException;
import com.bank.exception.TransactionFailedException;
import com.bank.dto.TransactionRequestDto;
import com.bank.dto.TransactionResponseDto;
import com.bank.dto.TransactionType;

public interface TransactionService {

    TransactionResponseDto depositorWithdrawAmount(TransactionRequestDto transactionRequestDto, TransactionType transactionType) throws InvalidAmountException, InvalidAccountNumberException, TransactionFailedException, InsufficientFundsException;
}
