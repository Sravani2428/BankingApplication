package com.bank.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor
public class PrioritizeCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prioritizeCustId;
    @Column(unique = true)
    @Getter
    private Integer customerId;
    private String customerName;
}
