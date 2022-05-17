package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import com.payMyBuddy.AppMoneyTransfert.model.DTO.UserInfo;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.UserHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.repository.TransactionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public UserHomePageInfo getHomePage(String email) {
        UserHomePageInfo userHomePageInfo = new UserHomePageInfo();
        userHomePageInfo.setRecentAddedUserInfo(new ArrayList<>());
        userHomePageInfo.setRecentTransactionsInfo(new ArrayList<>());

        User currentUser = userRepository.findByUsername(email);
        // Set the Balance
        userHomePageInfo.setUsername(currentUser.getFirstName());

        // Set the Balance
        userHomePageInfo.setBalance(currentUser.getBalance());
        
        // Set the recent added users
        Iterable<UserInfo> listUser = userRepository.getRecentUsersInfo(currentUser.getUserId());
        listUser.forEach(userHomePageInfo.getRecentAddedUserInfo()::add);
        
        // Set the recent transactions
        Iterable<TransactionInfo> listTransaction = transactionRepository.getRecentTransactionsInfo(currentUser.getUserId());
        listTransaction.forEach(userHomePageInfo.getRecentTransactionsInfo()::add);

        return userHomePageInfo;
    }
}
