package com.bank.dto;

import com.bank.entity.PrioritizeCustomer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrioritizeCustomerResponseDto {

    List<PrioritizeCustomer> prioritizeCustomerList;
}
