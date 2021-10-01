package com.bank.resource;

import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAccountNumberException;
import com.bank.exception.InvalidAmountException;
import com.bank.exception.TransactionFailedException;
import com.bank.dto.TransactionRequestDto;
import com.bank.dto.TransactionResponseDto;
import com.bank.dto.TransactionType;
import com.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionResource {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> depositAmount(@Valid @RequestBody TransactionRequestDto transactionRequestDto)
            throws InvalidAmountException, InvalidAccountNumberException, TransactionFailedException, InsufficientFundsException {

        log.info("Depositing Amount Started");
        TransactionResponseDto responseDto = transactionService.depositorWithdrawAmount(transactionRequestDto, TransactionType.DEPOSIT);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/withDraw")
    public ResponseEntity<TransactionResponseDto> amountWithdrawal(@Valid @RequestBody TransactionRequestDto transactionRequestDto)
            throws InvalidAccountNumberException, InsufficientFundsException, TransactionFailedException, InvalidAmountException {
        log.info("Withdrawing amount started");
        TransactionResponseDto responseDto = transactionService.depositorWithdrawAmount(transactionRequestDto, TransactionType.WITHDRAW);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
