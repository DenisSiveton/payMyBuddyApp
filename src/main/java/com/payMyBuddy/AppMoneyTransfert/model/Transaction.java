package com.payMyBuddy.AppMoneyTransfert.model;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;

import javax.persistence.*;

@NamedNativeQuery(name = "Transaction.getRecentTransactionsInfo",
        query="SELECT first_name as firstName,\n" +
                "CASE\n" +
                "\tWHEN user_sender_id =:user_id THEN -1*amount\n" +
                "    ELSE amount\n" +
                "END AS amount, description FROM Transaction\n" +
                "JOIN(SELECT connection_id, user_id\n" +
                "FROM relationship\n" +
                "WHERE user_id != :user_id) AS listConnection\n" +
                "ON Transaction.connection_id = listConnection.connection_id\n" +
                "JOIN User\n" +
                "ON User.id = listConnection.user_id\n" +
                "WHERE user_sender_id = :user_id OR user_receiver_id =:user_id\n" +
                "ORDER BY date DESC, transaction.id DESC LIMIT 3;",
        resultSetMapping = "Mapping.TransactionInfo")

@NamedNativeQuery(name = "Transaction.getTransactionInfoListFromUser",
        query="SELECT first_name AS firstName, description,\n" +
                "CASE\n" +
                "\tWHEN user_sender_id =:user_id THEN -1*amount\n" +
                "    ELSE amount\n" +
                "END AS amount\n" +
                "FROM Transaction\n" +
                "JOIN(SELECT connection_id, user_id\n" +
                "FROM relationship\n" +
                "WHERE user_id != :user_id) AS listConnection\n" +
                "ON Transaction.connection_id = listConnection.connection_id\n" +
                "JOIN User\n" +
                "ON User.id = listConnection.user_id\n" +
                "WHERE user_sender_id = :user_id OR user_receiver_id =:user_id\n" +
                "ORDER BY date DESC, transaction.id DESC;",
        resultSetMapping = "Mapping.TransactionInfo")

@SqlResultSetMapping(name = "Mapping.TransactionInfo",
        classes = @ConstructorResult(
                targetClass = TransactionInfo.class,
                columns = {@ColumnResult(name = "firstName"),
                        @ColumnResult(name = "description"),
                        @ColumnResult(name = "amount", type = Double.class)}))

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "connection_id")
    private Connection connection;

    @Column(name = "user_sender_id")
    private int senderId;

    @Column(name = "user_receiver_id")
    private int receiverId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private String date;

    @Column(name = "description")
    private String content;

    public Transaction(Connection connection, int senderId, int receiverId, Double amount, String date, String content) {
        this.connection = connection;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.date = date;
        this.content = content;
    }

    public Transaction(){}

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

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
