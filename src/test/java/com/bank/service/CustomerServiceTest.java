package com.bank.service;

import com.bank.dto.CustomerResponseDto;
import com.bank.entity.Customer;
import com.bank.exception.CustomerNotFoundException;
import com.bank.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    @Test
    void testGetCustomerDetails() throws CustomerNotFoundException {
        Customer customer = new Customer();
        Mockito.when(customerRepository.findById(1))
                .thenReturn(Optional.of(customer));
        CustomerResponseDto actual = customerServiceImpl.getCustomerDetails(1);
        assertEquals("Customer Details fetched successfully", actual.getMessage());
        assertEquals(200, actual.getStatusCode());

    }

    @Test
    void testInvalidCustomerId() {
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.getCustomerDetails(1));
    }

}
