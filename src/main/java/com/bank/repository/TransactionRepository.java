package com.bank.repository;

import com.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "select transaction FROM Transaction transaction WHERE  transaction.iBanNumber = :accountNumber and transaction.transactionDate BETWEEN :fromDate AND :toDate")
    List<Transaction> findTransactionsByDateRange(String accountNumber, LocalDate fromDate, LocalDate toDate);
}
