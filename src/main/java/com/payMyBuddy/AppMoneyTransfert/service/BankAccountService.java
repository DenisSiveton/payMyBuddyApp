package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.model.BankAccount;
import com.payMyBuddy.AppMoneyTransfert.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public BankAccount addBankAccount(BankAccount bankAccount){
        return bankAccountRepository.save(bankAccount);
    }
}
