package com.payMyBuddy.AppMoneyTransfert.controller;

import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Void> addTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction bankAccountAdded = transactionService.addTransaction(transaction);
        return new ResponseEntity(bankAccountAdded, HttpStatus.CREATED);
    }

    @PatchMapping("/transaction")
    public ResponseEntity<Void> updateTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction bankAccountModified = transactionService.updateTransaction(transaction);
        return new ResponseEntity(bankAccountModified, HttpStatus.NO_CONTENT);

    }
    @DeleteMapping("/transaction")
    public ResponseEntity<Void> deleteTransaction(@Valid @RequestBody Transaction transaction) {
        transactionService.deleteTransaction(transaction);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users")
    public ResponseEntity<Void> getAllTransactions() {
        Iterable<Transaction> listBankAccount = transactionService.getAllTransactions();
        return new ResponseEntity(listBankAccount, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Void> getTransactionById(@PathVariable("id") Integer id) {
        Optional<Transaction> listBankAccount = transactionService.getTransactionById(id);
        return new ResponseEntity(listBankAccount, HttpStatus.OK);
    }

}
