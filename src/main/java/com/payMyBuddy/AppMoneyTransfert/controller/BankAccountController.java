package com.payMyBuddy.AppMoneyTransfert.controller;

import com.payMyBuddy.AppMoneyTransfert.model.BankAccount;
import com.payMyBuddy.AppMoneyTransfert.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService){
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/bankAccount")
    public ResponseEntity<Void> addBankAccount(@Valid @RequestBody BankAccount bankAccount) {
        BankAccount bankAccountAdded = bankAccountService.addBankAccount(bankAccount);
        return new ResponseEntity(bankAccountAdded, HttpStatus.CREATED);
    }

    @PatchMapping("/bankAccount")
    public ResponseEntity<Void> updateBankAccount(@Valid @RequestBody BankAccount bankAccount) {
        BankAccount bankAccountModified = bankAccountService.updateBankAccount(bankAccount);
        return new ResponseEntity(bankAccountModified, HttpStatus.NO_CONTENT);

    }
    @DeleteMapping("/bankAccount")
    public ResponseEntity<Void> deleteBankAccount(@Valid @RequestBody BankAccount bankAccount) {
        bankAccountService.deleteBankAccount(bankAccount);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users")
    public ResponseEntity<Void> getAllBankAccounts() {
        Iterable<BankAccount> listBankAccount = bankAccountService.getAllBankAccounts();
        return new ResponseEntity(listBankAccount, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Void> getBankAccountById(@PathVariable("id") Integer id) {
        Optional<BankAccount> listBankAccount = bankAccountService.getBankAccountById(id);
        return new ResponseEntity(listBankAccount, HttpStatus.OK);
    }

}
