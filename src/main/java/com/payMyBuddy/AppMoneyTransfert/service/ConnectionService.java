package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    public Connection addRelationship(Connection connection){
        return connectionRepository.save(connection);
    }

    public Connection addConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    public Connection updateConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    public void deleteConnection(Connection connection) {
        connectionRepository.deleteById(connection.getConnectionId());
    }

    public Iterable<Connection> getAllTransactions() {
        return connectionRepository.findAll();
    }

    public Optional<Connection> getTransactionById(Integer id) {
        return connectionRepository.findById(id);
    }
}
