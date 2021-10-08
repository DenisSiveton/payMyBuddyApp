package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.model.BankAccount;
import com.payMyBuddy.AppMoneyTransfert.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public BankAccount addBankAccount(BankAccount bankAccount){
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount updateBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccountRepository.deleteById(bankAccount.getBankAccountId());
    }

    public Iterable<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccountById(Integer id) {
        return bankAccountRepository.findById(id);
    }
}
