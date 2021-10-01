package com.bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import com.bank.dto.TransactionRequestDto;
import com.bank.dto.TransactionResponseDto;
import com.bank.dto.TransactionType;
import com.bank.entity.Account;
import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAccountNumberException;
import com.bank.exception.InvalidAmountException;
import com.bank.exception.TransactionFailedException;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    private TransactionRequestDto transactionRequestDto;

    private Transaction transaction;

    private Account account;

    private Customer customer;

    @BeforeEach
    void setup() {
        transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAccountNumber("12345");
        transactionRequestDto.setAmount(1000.00);
        transaction = new Transaction();
        transaction.setTransactionId(1);
        account = Account.builder().accountId(1).accountType("Savings")
                .ibanNumber("112233").balance(10000.00).build();
    }

    @Test
    void testDepositAmountOk() throws InvalidAmountException, InvalidAccountNumberException, TransactionFailedException, InsufficientFundsException {
        Mockito.when(accountRepository.findAccountByIbanNumber(anyString()))
                .thenReturn(Optional.of(account));
        Mockito.when(accountRepository.update(anyDouble(), anyString())).thenReturn(1);
        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);
        TransactionResponseDto actual = transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.DEPOSIT);
        assertEquals(200, actual.getStatusCode());
    }

    @Test
    void testInvalidAccountNumber() {
        Mockito.when(accountRepository.findAccountByIbanNumber(anyString()))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(InvalidAccountNumberException.class, () -> transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.DEPOSIT));
    }

    @Test
    void testInvalidDepositAmount()  {
        transactionRequestDto.setAmount(-1000.00);
        Mockito.when(accountRepository.findAccountByIbanNumber(anyString())).thenReturn(Optional.of(account));
        Assertions.assertThrows(InvalidAmountException.class, () -> transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.DEPOSIT));
    }
    @Test
    void testDepositTransactionSaveException() {
        Mockito.when(accountRepository.findAccountByIbanNumber(anyString()))
                .thenReturn(Optional.of(account));
        Mockito.when(accountRepository.update(anyDouble(), anyString())).thenReturn(1);
        Transaction transaction = new Transaction();
        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);
        Assertions.assertThrows(TransactionFailedException.class, () -> transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.DEPOSIT));
    }
    @Test
    void testWithDrawAmountOk() throws InvalidAccountNumberException, InsufficientFundsException, TransactionFailedException, InvalidAmountException {
        Mockito.when(accountRepository.findAccountByIbanNumber(anyString()))
                .thenReturn(Optional.of(account));
        Mockito.when(accountRepository.update(anyDouble(), anyString())).thenReturn(1);
        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);
        TransactionResponseDto actual = transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.WITHDRAW);
        assertEquals(200, actual.getStatusCode());
    }

    @Test
    void testInvalidAccount() {
        Mockito.when(accountRepository.findAccountByIbanNumber(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(InvalidAccountNumberException.class, () -> transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.WITHDRAW));
    }

    @Test
    void testInvalidWithDrawAmount()  {
        transactionRequestDto.setAmount(-1000.00);
        Mockito.when(accountRepository.findAccountByIbanNumber(Mockito.any())).thenReturn(Optional.of(account));
        Assertions.assertThrows(InvalidAmountException.class, () -> transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.WITHDRAW));
    }

    @Test
    void testWithDrawTransactionSaveException() {
        Mockito.when(accountRepository.findAccountByIbanNumber(anyString()))
                .thenReturn(Optional.of(account));
        Mockito.when(accountRepository.update(anyDouble(), anyString())).thenReturn(1);
        Transaction transaction = new Transaction();
        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);
        Assertions.assertThrows(TransactionFailedException.class, () -> transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.WITHDRAW));
    }

    @Test
    void testInsufficientFundsException() {
        Mockito.when(accountRepository.findAccountByIbanNumber(anyString()))
                .thenReturn(Optional.of(account));
        transactionRequestDto.setAmount(9800.00);
        Assertions.assertThrows(InsufficientFundsException.class, () -> transactionServiceImpl.depositorWithdrawAmount(transactionRequestDto, TransactionType.WITHDRAW));
    }
}
