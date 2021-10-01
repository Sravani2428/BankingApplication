package com.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
public class CustomerResponseDto {
    @Getter
    private int statusCode;
    @Getter
    private String message;
    private String firstName;
    private String middleName;
    private String lastName;

}
