package com.payMyBuddy.AppMoneyTransfert.controller;

import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.service.ConnectionService;
import com.payMyBuddy.AppMoneyTransfert.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService){
        this.connectionService = connectionService;
    }

    @PostMapping("/connection")
    public ResponseEntity<Void> addConnection(@Valid @RequestBody Connection connection) {
        Connection connectionAdded = connectionService.addConnection(connection);
        return new ResponseEntity(connectionAdded, HttpStatus.CREATED);
    }

    @PatchMapping("/connection")
    public ResponseEntity<Void> updateConnection(@Valid @RequestBody Connection connection) {
        Connection connectionModified = connectionService.updateConnection(connection);
        return new ResponseEntity(connectionModified, HttpStatus.NO_CONTENT);

    }
    @DeleteMapping("/connection")
    public ResponseEntity<Void> deleteConnection(@Valid @RequestBody Connection connection) {
        connectionService.deleteConnection(connection);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/connections")
    public ResponseEntity<Void> getAllTransactions() {
        Iterable<Connection> listConnection = connectionService.getAllTransactions();
        return new ResponseEntity(listConnection, HttpStatus.OK);
    }

    @GetMapping("/connection/{id}")
    public ResponseEntity<Void> getTransactionById(@PathVariable("id") Integer id) {
        Optional<Connection> connection = connectionService.getTransactionById(id);
        return new ResponseEntity(connection, HttpStatus.OK);
    }

}
