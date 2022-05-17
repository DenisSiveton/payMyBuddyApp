package com.payMyBuddy.AppMoneyTransfert.model;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.UserInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(name = "User.getRecentUsersInfo",
                  query="SELECT email, first_name as firstName, last_name as lastName FROM User\n" +
            "JOIN (SELECT connection_id, user_id\n" +
            "FROM Relationship\n" +
            "JOIN (SELECT connection_id AS id FROM Relationship where user_id =:user_id) AS connections\n" +
            "ON connection_id = connections.id\n" +
            "group by connection_id, user_id\n" +
            "HAVING user_id != :user_id) as Contacts\n" +
            "ON user.id = Contacts.user_id\n" +
            "JOIN Connection\n" +
            "ON connection.id = Contacts.connection_id\n" +
            "group by email, firstName, lastName\n"+
            "order by date_start DESC, connection_id DESC \n" +
            "LIMIT 3",
                  resultSetMapping = "Mapping.UserInfo")
@SqlResultSetMapping(name = "Mapping.UserInfo",
classes = @ConstructorResult(
        targetClass = UserInfo.class,
        columns = {@ColumnResult(name = "email"),
                   @ColumnResult(name = "firstName"),
                   @ColumnResult(name = "lastName")}))
@Entity
@Table(name = "user")
public class User{

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Double balance;

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

    public User(String firstName, String lastName, String email, String password, Double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = email;
        this.password = password;
        this.balance = balance;
    }
    public User(){

    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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
