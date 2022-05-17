package com.payMyBuddy.AppMoneyTransfert.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "connection")
public class Connection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int connectionId;

    @Column(name = "date_start")
    private String dateStart;
    @ManyToMany(
            mappedBy = "connections",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            }
    )
    private List<User> users = new ArrayList<>();

    @OneToMany(
            mappedBy = "connection",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> transactions = new ArrayList<>();

    //Getters and Setters
    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    //Helpers methods
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setConnection(this);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setConnection(null);
    }

}
