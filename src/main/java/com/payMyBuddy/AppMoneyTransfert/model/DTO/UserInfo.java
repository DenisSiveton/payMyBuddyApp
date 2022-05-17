package com.payMyBuddy.AppMoneyTransfert.model.DTO;

public class UserInfo {

    private String email;

    private String firstName;

    private String lastName;

    public UserInfo(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
