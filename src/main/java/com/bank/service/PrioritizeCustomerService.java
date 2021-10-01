package com.bank.service;

import com.bank.exception.PrioritizeCustomersNotFoundException;
import com.bank.dto.PrioritizeCustomerResponseDto;

public interface PrioritizeCustomerService {

     PrioritizeCustomerResponseDto findPrioritizeCustomers() throws PrioritizeCustomersNotFoundException;
}
