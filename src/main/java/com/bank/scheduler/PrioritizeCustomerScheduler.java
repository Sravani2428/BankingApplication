package com.bank.scheduler;

import com.bank.entity.Customer;
import com.bank.entity.PrioritizeCustomer;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.PrioritizeCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PrioritizeCustomerScheduler {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final PrioritizeCustomerRepository prioritizeCustomerRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    String kafkaTopic = "prioritize_customer_topic";

    public void send(String message) {
        kafkaTemplate.send(kafkaTopic, message);
    }

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void sendPrioritizeCustomersKafka(){
        removePrioritizeCustomers();
        addPrioritizeCustomers();
    }

    public void addPrioritizeCustomers(){
        List<Integer> prioritizeCustomer = accountRepository.findPrioritizeCustomer();
        List<Customer> customerList = customerRepository.findAllById(prioritizeCustomer);
        Set<Integer> customerIdList = new HashSet<>();
        List<String> listSendToKafka = new ArrayList<>();
        List<PrioritizeCustomer> priCustomers = prioritizeCustomerRepository.findAll();
        priCustomers.stream().map(p -> customerIdList.add(p.getCustomerId())).collect(Collectors.toList());
        List<PrioritizeCustomer> prioritizeCustomerList = new ArrayList<>();
        customerList.forEach(customer -> {
            if( !customerIdList.contains(customer.getId())){
                PrioritizeCustomer prioritizeCust = new PrioritizeCustomer();
                prioritizeCust.setCustomerId(customer.getId());
                String name = customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getLastName();
                prioritizeCust.setCustomerName(name);
                prioritizeCustomerList.add(prioritizeCust);
                listSendToKafka.add(name);
            }
        });
        if(!prioritizeCustomerList.isEmpty()){
            prioritizeCustomerRepository.saveAll(prioritizeCustomerList);
            send(listSendToKafka.toString());
        }
    }

    public void removePrioritizeCustomers(){
        List<Integer> nonPrioritizeCustomer = accountRepository.findNonPrioritizeCustomer();
        prioritizeCustomerRepository.deleteAllById(nonPrioritizeCustomer);
    }
}
