package com.payMyBuddy.AppMoneyTransfert.model;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "description")
    private String content;
}
