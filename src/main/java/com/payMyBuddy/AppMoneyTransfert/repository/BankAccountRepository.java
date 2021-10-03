package com.payMyBuddy.AppMoneyTransfert.repository;

import com.payMyBuddy.AppMoneyTransfert.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
}
