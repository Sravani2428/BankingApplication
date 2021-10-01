package com.bank.service;

import com.bank.dto.TransactionsResponseDto;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.exception.TransactionsNotFoundException;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class TransactionStatementServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionStatementServiceImpl transactionStatementServiceImpl;

    private Account account;

    @BeforeEach
    void setup() {
        account = Account.builder().accountId(1).accountType("Savings")
                .ibanNumber("112233").balance(10000.00).build();
    }
    @Test
    void testGetTransactionsOk() throws TransactionsNotFoundException {
    List<Transaction> transactionList = new ArrayList<>();
    Transaction transaction = new Transaction();
    transactionList.add(transaction);
    Mockito.when(accountRepository.findAccountByCustomerId(1)).thenReturn(Optional.of(account));
    Mockito.when(transactionRepository.findTransactionsByDateRange(anyString(),any(LocalDate .class),any(LocalDate.class))).thenReturn(transactionList);
    LocalDate fromDate = LocalDate.parse("2021-09-19");
    LocalDate toDate = LocalDate.parse("2021-09-20");
    TransactionsResponseDto responseDto = transactionStatementServiceImpl.getStatement(1, fromDate, toDate);
    Assertions.assertEquals(transactionList.size(), responseDto.getTransactionList().size());
    }

    @Test
    void testTransactionsNotFoundException()  {
        List<Transaction> transactionList = new ArrayList<>();
        Mockito.when(accountRepository.findAccountByCustomerId(1)).thenReturn(Optional.of(account));
        Mockito.when(transactionRepository.findTransactionsByDateRange(anyString(),any(LocalDate .class),any(LocalDate.class))).thenReturn(transactionList);
        LocalDate fromDate = LocalDate.parse("2021-09-19");
        LocalDate toDate = LocalDate.parse("2021-09-20");
        Assertions.assertThrows(TransactionsNotFoundException.class, () -> transactionStatementServiceImpl.getStatement(1, fromDate, toDate));
    }
}
