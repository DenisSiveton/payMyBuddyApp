package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.model.Relationship;
import com.payMyBuddy.AppMoneyTransfert.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    public Relationship addRelationship(Relationship relationship){
        return relationshipRepository.save(relationship);
    }
}
