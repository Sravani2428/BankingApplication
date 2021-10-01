package com.bank.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @Column(name = "customer_id")
    @Getter
    private Integer id;

    @Column
    @Getter
    private String firstName;

    @Column
    @Getter
    private String middleName;

    @Column
    @Getter
    private String lastName;

    @OneToOne(mappedBy="customer", fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    private Account account;

}
