package com.bank.service;

import com.bank.exception.TransactionsNotFoundException;
import com.bank.dto.TransactionsResponseDto;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionStatementServiceImpl implements TransactionStatementService{

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;
    @Override
    public TransactionsResponseDto getStatement(Integer customerId, LocalDate fromDate, LocalDate toDate) throws TransactionsNotFoundException {
        log.info("Fetching Customer's statement");
        List<Transaction> statementList = new ArrayList<>();
        Optional<Account> account = accountRepository.findAccountByCustomerId(customerId);
        if(account.isPresent()) {
            statementList = transactionRepository.findTransactionsByDateRange(account.get().getIbanNumber(), fromDate, toDate);
        }
        if(!statementList.isEmpty()) {
            return new TransactionsResponseDto(statementList);
        }
        else {
            log.error("No transactions found");
            throw new TransactionsNotFoundException("No transactions found in the given Date range for Customer");
        }
    }
}
