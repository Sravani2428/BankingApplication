package com.bank.service;

import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAccountNumberException;
import com.bank.exception.InvalidAmountException;
import com.bank.exception.TransactionFailedException;
import com.bank.dto.TransactionRequestDto;
import com.bank.dto.TransactionResponseDto;
import com.bank.dto.TransactionType;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Transactional
    @Override
    public TransactionResponseDto depositorWithdrawAmount(TransactionRequestDto transactionRequestDto, TransactionType transactionType) throws InvalidAmountException,
            InvalidAccountNumberException, TransactionFailedException, InsufficientFundsException {
        log.info("Starting" + transactionType.name() + "Amount");
        Optional<Account> account = accountRepository.findAccountByIbanNumber(transactionRequestDto.getAccountNumber());
        if (account.isPresent()) {
            if (transactionRequestDto.getAmount() <= 0) {
                log.error("Invalid Amount. Amount should be greater than Zero");
                throw new InvalidAmountException("Invalid Amount. Amount should be greater than Zero");
            }
            Double amountToUpdate = calculateAmount(transactionType, account.get().getBalance(), transactionRequestDto.getAmount());
            accountRepository.update(amountToUpdate, transactionRequestDto.getAccountNumber());
            Transaction transaction = Transaction.builder().iBanNumber(account.get().getIbanNumber())
                    .amount(transactionRequestDto.getAmount())
                   .transactionDate(LocalDate.now())
                    .transactionType(transactionType.name()).build();
            if (Optional.ofNullable(transactionRepository.save(transaction).getTransactionId()).isPresent()) {
                TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
                transactionResponseDto.setMessage("Amount  " + transactionRequestDto.getAmount() + " " + transactionType.name() + " Successfully");
                transactionResponseDto.setStatusCode(HttpStatus.OK.value());
                return transactionResponseDto;
            } else {
                    log.error("Error while saving the transaction");
                    throw new TransactionFailedException("Transaction Failed. Please try later");
            }
        } else {
            throw new InvalidAccountNumberException("Invalid Account Number");
        }
    }

    private Double calculateAmount(TransactionType type, Double balance, Double amount) throws InsufficientFundsException {
        Double calculatedAmount = null;
            if(type.equals(TransactionType.DEPOSIT)){
                calculatedAmount = balance + amount;
            }else if(type.equals(TransactionType.WITHDRAW)){
                calculatedAmount = balance - amount;
                if(calculatedAmount <= 500){
                    log.error("Insufficient Balance");
                    throw new InsufficientFundsException("Insufficient Balance");
                }
            }
            return calculatedAmount;
    }
}
