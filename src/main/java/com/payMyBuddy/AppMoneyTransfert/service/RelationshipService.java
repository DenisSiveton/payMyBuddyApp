package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    public Connection addRelationship(Connection connection){
        return relationshipRepository.save(connection);
    }
}
