package com.bank.resource;

import com.bank.exception.TransactionsNotFoundException;
import com.bank.dto.TransactionsResponseDto;
import com.bank.service.TransactionStatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionStatementResource {

    private final TransactionStatementService transactionStatementService;

    @GetMapping("/transactions/{customerId}")
    public ResponseEntity<TransactionsResponseDto> getTransactions(@PathVariable("customerId") Integer cusId, @RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate,
                                                                   @RequestParam("toDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate) throws TransactionsNotFoundException {
        log.info("Entering Transaction statement resource");
        TransactionsResponseDto response = transactionStatementService.getStatement(cusId,fromDate,toDate);
        return ResponseEntity.ok(response);
    }

}
