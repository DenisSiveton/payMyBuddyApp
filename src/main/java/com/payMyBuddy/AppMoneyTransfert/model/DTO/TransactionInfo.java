package com.payMyBuddy.AppMoneyTransfert.model.DTO;

public class TransactionInfo {

    private String firstName;

    private String description;

    private double amount;

    public TransactionInfo(String first_name, String description, double amount) {
        this.firstName = first_name;
        this.description = description;
        this.amount = amount;
    }

    public TransactionInfo() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
