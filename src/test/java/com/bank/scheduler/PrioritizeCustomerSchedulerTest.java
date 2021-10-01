package com.bank.scheduler;


import com.bank.entity.Customer;
import com.bank.entity.PrioritizeCustomer;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.PrioritizeCustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class PrioritizeCustomerSchedulerTest {

    @InjectMocks
    private PrioritizeCustomerScheduler prioritizeCustomerScheduler;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PrioritizeCustomerRepository prioritizeCustomerRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ListenableFuture<SendResult<String, String>> future;


    @Test
    void testPrioritizeCustomerScheduler()  {
        List<Integer> prioritizeCustomerList = new ArrayList<>();
        prioritizeCustomerList.add(1);
        prioritizeCustomerList.add(2);
        List<Customer> customerList = new ArrayList<>();
        Customer customer = Customer.builder().id(1).firstName("Sravani").build();
        customerList.add(customer);
        List<PrioritizeCustomer> priCustomers = new ArrayList<>();
        List<PrioritizeCustomer> priCustomers1 = new ArrayList<>();
        PrioritizeCustomer prioritizeCustomer = new PrioritizeCustomer();
        prioritizeCustomer.setPrioritizeCustId(1);
        prioritizeCustomer.setCustomerId(1);
        prioritizeCustomer.setCustomerName("Sravani");
        priCustomers1.add(prioritizeCustomer);
        Mockito.when(accountRepository.findPrioritizeCustomer()).thenReturn(prioritizeCustomerList);
        Mockito.when(customerRepository.findAllById(any())).thenReturn(customerList);
        Mockito.when(prioritizeCustomerRepository.findAll()).thenReturn(priCustomers);
        Mockito.when(prioritizeCustomerRepository.saveAll(any())).thenReturn(priCustomers1);
        Mockito.when(kafkaTemplate.send(anyString(),anyString())).thenReturn(future);
        prioritizeCustomerScheduler.sendPrioritizeCustomersKafka();
    }
    @Test
    void testPrioritizeCustomerListEmpty()  {
        List<Integer> prioritizeCustomerList = new ArrayList<>();
        prioritizeCustomerList.add(1);
        prioritizeCustomerList.add(2);
        List<Customer> customerList = new ArrayList<>();
        Customer customer = Customer.builder().id(1).firstName("Sravani").build();
        customerList.add(customer);
        List<PrioritizeCustomer> priCustomers = new ArrayList<>();
        List<PrioritizeCustomer> priCustomers1 = new ArrayList<>();
        PrioritizeCustomer prioritizeCustomer = new PrioritizeCustomer();
        prioritizeCustomer.setPrioritizeCustId(1);
        prioritizeCustomer.setCustomerId(1);
        prioritizeCustomer.setCustomerName("Sravani");
        priCustomers.add(prioritizeCustomer);
        priCustomers1.add(prioritizeCustomer);
        Mockito.when(accountRepository.findPrioritizeCustomer()).thenReturn(prioritizeCustomerList);
        Mockito.when(customerRepository.findAllById(any())).thenReturn(customerList);
        Mockito.when(prioritizeCustomerRepository.findAll()).thenReturn(priCustomers);
        prioritizeCustomerScheduler.sendPrioritizeCustomersKafka();
    }
}
