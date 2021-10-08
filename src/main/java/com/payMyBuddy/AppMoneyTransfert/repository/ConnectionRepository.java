package com.payMyBuddy.AppMoneyTransfert.repository;

import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Integer> {
}
