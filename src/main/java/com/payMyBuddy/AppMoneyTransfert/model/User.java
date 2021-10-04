package com.payMyBuddy.AppMoneyTransfert.model;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(name = "relationship",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "connection_id")
    )
    private List<Connection> connections = new ArrayList<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    //Helpers methods
        //For the BankAccounts
    public void addBankAccount(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
        bankAccount.setUser(this);
    }

    public void removeBankAccount(BankAccount bankAccount) {
        bankAccounts.remove(bankAccount);
        bankAccount.setUser(null);
    }

        //For the Connections
    public void addConnection(Connection connection) {
        connections.add(connection);
        connection.getUsers().add(this);
    }

    public void removeConnection(Connection connection) {
        connections.remove(connection);
        connection.getUsers().remove(this);
    }

}
