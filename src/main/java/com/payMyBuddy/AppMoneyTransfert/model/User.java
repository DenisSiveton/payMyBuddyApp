package com.payMyBuddy.AppMoneyTransfert.model;

import org.springframework.jca.endpoint.GenericMessageEndpointFactory;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "account_password")
    private String accountPassword;

    @Column(name = "account_balance")
    private Double accountBalance;
}
