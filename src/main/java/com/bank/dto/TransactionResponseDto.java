package com.bank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
public class TransactionResponseDto {
        @Getter
        private int statusCode;
        private String message;

}
