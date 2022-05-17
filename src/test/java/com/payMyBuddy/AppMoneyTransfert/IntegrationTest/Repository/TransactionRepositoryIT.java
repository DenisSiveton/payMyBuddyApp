package com.payMyBuddy.AppMoneyTransfert.IntegrationTest.Repository;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import com.payMyBuddy.AppMoneyTransfert.repository.TransactionRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class TransactionRepositoryIT {

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void getRecentTransactionsTest_ShouldHaveCorrectRecentTransactionInfo(){
        //ARRANGE
        int userIdTest = 1;

        //ACT
        Iterable<TransactionInfo> transactionInfoIterable = transactionRepository.getRecentTransactionsInfo(userIdTest);
        List<TransactionInfo> transactionInfoList = new ArrayList<>();
        transactionInfoIterable.forEach(transactionInfoList::add);

        //ASSERT
        assertThat(transactionInfoList.size()).isEqualTo(3);
        assertThat(transactionInfoList.get(2).getFirstName()).isEqualTo("Monique");
    }

    @Test
    public void getRecentTransactionsTest_ShouldHaveOnly3TransactionInfo(){
        //ARRANGE
        int userIdTest = 1;

        //ACT
        Iterable<TransactionInfo> transactionInfoIterable = transactionRepository.getRecentTransactionsInfo(userIdTest);
        List<TransactionInfo> transactionInfoList = new ArrayList<>();
        transactionInfoIterable.forEach(transactionInfoList::add);

        //ASSERT
        assertThat(transactionInfoList.size()).isEqualTo(3);
        assertThat(transactionInfoList.get(1).getDescription()).isEqualTo("transaction4");
        assertThat(transactionInfoList.get(2).getDescription()).isEqualTo("transaction3");
    }


    @Test
    public void getTransactionInfoListFromUserTest_ShouldHaveCorrectTransactionInfo(){
        //ARRANGE
        int userIdTest = 1;

        //ACT
        Iterable<TransactionInfo> transactionInfoIterable = transactionRepository.getTransactionInfoListFromUser(userIdTest);
        List<TransactionInfo> transactionInfoList = new ArrayList<>();
           transactionInfoIterable.forEach(transactionInfoList::add);

        //ASSERT
        assertThat(transactionInfoList.size()).isEqualTo(5);
        assertThat(transactionInfoList.get(4).getAmount()).isEqualTo(-5.0);
    }
}
