package com.bank.dto;

import com.bank.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsResponseDto {

    List<Transaction> transactionList;

}
