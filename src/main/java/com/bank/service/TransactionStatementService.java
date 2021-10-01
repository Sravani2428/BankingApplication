package com.bank.service;

import com.bank.exception.TransactionsNotFoundException;
import com.bank.dto.TransactionsResponseDto;

import java.time.LocalDate;

public interface TransactionStatementService {

    TransactionsResponseDto getStatement(Integer customerId, LocalDate fromDate, LocalDate toDate) throws TransactionsNotFoundException;
}
