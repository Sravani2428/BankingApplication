package com.bank.resource;

import com.bank.exception.PrioritizeCustomersNotFoundException;
import com.bank.dto.PrioritizeCustomerResponseDto;
import com.bank.service.PrioritizeCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PrioritizeCustomerResource {

    private final PrioritizeCustomerService prioritizeCustomerService;

    @GetMapping("/prioritizeCustomers")
    public ResponseEntity<PrioritizeCustomerResponseDto> getPrioritizeCustomers() throws PrioritizeCustomersNotFoundException {
        log.info("Entering prioritize customers resource");
        PrioritizeCustomerResponseDto prioritizeCustomerResponseDto = prioritizeCustomerService.findPrioritizeCustomers();
        return new ResponseEntity<>(prioritizeCustomerResponseDto, HttpStatus.OK);
    }
}
