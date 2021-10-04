package com.payMyBuddy.AppMoneyTransfert.model;

import org.springframework.cache.interceptor.CacheAspectSupport;

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

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "connection_id")
    private Connection connection;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
