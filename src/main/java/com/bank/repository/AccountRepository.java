package com.bank.repository;

import com.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountByIbanNumber(String accountNumber);

    @Modifying
    @Query(value = "UPDATE Account SET  balance = :amount where ibanNumber = :accountNumber")
    Integer update(Double amount, String accountNumber);

    @Query("SELECT c.id FROM Customer c, Account a WHERE c.id = a.customer.id and a.balance >= 10000")
    List<Integer> findPrioritizeCustomer();

    @Query("SELECT c.id FROM Customer c, Account a WHERE c.id = a.customer.id and a.balance < 10000")
    List<Integer> findNonPrioritizeCustomer();

    Optional<Account> findAccountByCustomerId(Integer customerId);
}
