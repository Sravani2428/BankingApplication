package com.bank.resource;

import com.bank.exception.CustomerNotFoundException;
import com.bank.dto.CustomerResponseDto;
import com.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;

    @GetMapping("/getCustomerDetails")
    public ResponseEntity<CustomerResponseDto> getCustomerDetails(@RequestParam Integer customerId)
            throws CustomerNotFoundException {

        CustomerResponseDto responseDto = customerService.getCustomerDetails(customerId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
