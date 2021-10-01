package com.bank.service;

import com.bank.exception.CustomerNotFoundException;
import com.bank.dto.CustomerResponseDto;

public interface CustomerService {
    CustomerResponseDto getCustomerDetails(Integer customerId) throws CustomerNotFoundException;
}
