package com.payMyBuddy.AppMoneyTransfert.model.viewModel;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import com.payMyBuddy.AppMoneyTransfert.model.DTO.UserInfo;

import java.util.List;

public class UserHomePageInfo {

    private String username;

    private double balance;

    private List<TransactionInfo> recentTransactionsInfo;

    private List<UserInfo> recentAddedUserInfo;

    public UserHomePageInfo() {
    }

    public UserHomePageInfo(String username, double balance, List<TransactionInfo> recentTransactionsInfo, List<UserInfo> recentAddedUserInfo) {
        this.username = username;
        this.balance = balance;
        this.recentTransactionsInfo = recentTransactionsInfo;
        this.recentAddedUserInfo = recentAddedUserInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<TransactionInfo> getRecentTransactionsInfo() {
        return recentTransactionsInfo;
    }

    public void setRecentTransactionsInfo(List<TransactionInfo> recentTransactionsInfo) {
        this.recentTransactionsInfo = recentTransactionsInfo;
    }

    public List<UserInfo> getRecentAddedUserInfo() {
        return recentAddedUserInfo;
    }

    public void setRecentAddedUserInfo(List<UserInfo> recentAddedUserInfo) {
        this.recentAddedUserInfo = recentAddedUserInfo;
    }
}
