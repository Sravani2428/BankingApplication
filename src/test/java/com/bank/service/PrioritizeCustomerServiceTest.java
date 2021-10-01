package com.bank.service;

import com.bank.dto.PrioritizeCustomerResponseDto;
import com.bank.entity.PrioritizeCustomer;
import com.bank.exception.PrioritizeCustomersNotFoundException;
import com.bank.repository.PrioritizeCustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PrioritizeCustomerServiceTest {

    @Mock
    PrioritizeCustomerRepository prioritizeCustomerRepository;

    @InjectMocks
    PrioritizeCustomerServiceImpl prioritizeCustomerService;

    @Test
    void testPrioritizeCustomersOk() throws PrioritizeCustomersNotFoundException {
        List<PrioritizeCustomer> prioritizeCustomersList = new ArrayList<>();
        PrioritizeCustomer prioritizeCustomer = new PrioritizeCustomer();
        prioritizeCustomersList.add(prioritizeCustomer);
        Mockito.when(prioritizeCustomerRepository.findAll()).thenReturn(prioritizeCustomersList);
        PrioritizeCustomerResponseDto responseDto = prioritizeCustomerService.findPrioritizeCustomers();
        Assertions.assertEquals(prioritizeCustomersList.size(), responseDto.getPrioritizeCustomerList().size());
    }

    @Test
    void testPrioritizeCustomersNotFound() {
        List<PrioritizeCustomer> prioritizeCustomersList = new ArrayList<>();
        Mockito.when(prioritizeCustomerRepository.findAll()).thenReturn(prioritizeCustomersList);
        Assertions.assertThrows(PrioritizeCustomersNotFoundException.class, () -> prioritizeCustomerService.findPrioritizeCustomers());
    }
}
