package com.payMyBuddy.AppMoneyTransfert.model;

import javax.persistence.*;

@Entity
@Table(name = "bankAccount")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bankAccountId;

    @Column(name = "bankAccount_iban")
    private String iban;


}
