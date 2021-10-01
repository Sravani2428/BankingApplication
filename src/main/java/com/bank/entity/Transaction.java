package com.bank.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer transactionId;
    private String iBanNumber;
    private String transactionType;
    private Double amount;
    private LocalDate transactionDate;

}
