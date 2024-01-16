package com.runthebank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID customerId;

    private String name;

    private String email;

    private String document;

    private String address;

    private String password;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts = new ArrayList<>();


}
