package com.bank.repository;

import com.bank.entity.PrioritizeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioritizeCustomerRepository extends JpaRepository<PrioritizeCustomer,Integer> {
}
