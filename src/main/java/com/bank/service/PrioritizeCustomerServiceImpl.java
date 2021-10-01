package com.bank.service;
import com.bank.exception.PrioritizeCustomersNotFoundException;
import com.bank.dto.PrioritizeCustomerResponseDto;
import com.bank.entity.PrioritizeCustomer;
import com.bank.repository.PrioritizeCustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrioritizeCustomerServiceImpl implements PrioritizeCustomerService {

    private final PrioritizeCustomerRepository prioritizeCustomerRepository;

    @Override
    public PrioritizeCustomerResponseDto findPrioritizeCustomers() throws PrioritizeCustomersNotFoundException {
        log.info("Fetching Prioritize Customers");
        List<PrioritizeCustomer> prioritizeCustomers = prioritizeCustomerRepository.findAll();
        if(!prioritizeCustomers.isEmpty()) {
            return new PrioritizeCustomerResponseDto(prioritizeCustomers);
        }else{
            log.error("No Prioritize Customers found");
            throw new PrioritizeCustomersNotFoundException("No Prioritize Customers");
        }
    }
}
