package com.bank.service;

import com.bank.exception.CustomerNotFoundException;
import com.bank.dto.CustomerResponseDto;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDto getCustomerDetails(Integer customerId) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            CustomerResponseDto customerResponseDto = new CustomerResponseDto();
            customerResponseDto.setFirstName(customer.get().getFirstName());
            customerResponseDto.setMiddleName(customer.get().getMiddleName());
            customerResponseDto.setLastName(customer.get().getLastName());
            customerResponseDto.setMessage("Customer Details fetched successfully");
            customerResponseDto.setStatusCode(HttpStatus.OK.value());
            return customerResponseDto;

        }else{
            throw new CustomerNotFoundException("Customer Id invalid");
        }

    }
}
