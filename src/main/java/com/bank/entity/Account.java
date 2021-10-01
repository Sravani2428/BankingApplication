package com.bank.entity;
import lombok.*;
import javax.persistence.*;


@Entity
@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column
    @Getter
    private String ibanNumber;

    @Column
    private String accountType;

    @Column
    @Getter
    private Double balance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
