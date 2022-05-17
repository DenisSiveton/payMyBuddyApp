package com.payMyBuddy.AppMoneyTransfert.UnitTest.Service;

import com.payMyBuddy.AppMoneyTransfert.exception.BalanceInsufficientException;
import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.TransferHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.repository.ConnectionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.TransactionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import com.payMyBuddy.AppMoneyTransfert.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(TransactionService.class)
public class TransactionServiceTest {
    @Autowired
    TransactionService transactionService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    TransactionRepository transactionRepository;
    @MockBean
    ConnectionRepository connectionRepository;

    static Transaction transactionTest;
    static User userTest;
    static User receiverTest;

    @BeforeEach
    public void test_init(){
        transactionTest = new Transaction();
        userTest = new User("Jean", "Durand", "j.d@hotmail.com", "1234", 100.00);
        userTest.setUserId(-1);
        receiverTest = new User("Harry", "Potter", "h.p@hotmail.com", "gryffindor", 100.00);
        receiverTest.setUserId(0);

    }

    @Test
    public void addTransactionTest_seeIfCorrectMethodIsCalled() throws BalanceInsufficientException {
        //ARRANGE
        String usernameTest = "j.d@hotmail.com";
        String emailTest = "h.p@hotmail.com";
        double amountTest  = 1.00;
        String descriptionTest = "test";

        when(userRepository.findByUsername(usernameTest)).thenReturn(userTest);
        when(userRepository.findByUsername(emailTest)).thenReturn(receiverTest);
        when(connectionRepository.getConnectionIdFromTwoUsers(userTest.getUserId(), receiverTest.getUserId())).thenReturn(java.util.Optional.of(new Connection()));
        when(transactionRepository.save(transactionTest)).thenReturn(transactionTest);

        //ACT
        Transaction addedTransaction = transactionService.addTransaction(usernameTest, emailTest, amountTest, descriptionTest);

        //ASSERT
        verify(userRepository, times(1)).findByUsername(usernameTest);
        verify(userRepository, times(1)).findByUsername(emailTest);
        verify(connectionRepository, times(1)).getConnectionIdFromTwoUsers(-1, 0);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(userRepository, times(1)).updateUsersAccountAfterTransaction(-1,0,amountTest);
    }

    @Test
    public void getTransferHomePageInfoTest_seeIfCorrectMethodIsCalled(){
        //ARRANGE
        int userId = 1;
        String userEmail = "j.d@hotmail.com";

        List<String> listEmail = new ArrayList<>();
        listEmail.add("m.j@hotmail.com");
        listEmail.add("d.d@hotmail.com");
        listEmail.add("g.s@hotmail.com");
        Iterable<String> userEmailList = listEmail;

        List<TransactionInfo> listTransactionInfo = new ArrayList<>();
        listTransactionInfo.add(new TransactionInfo("John", "wedding", 50.00));
        listTransactionInfo.add(new TransactionInfo("John", "BDay", 50.00));
        listTransactionInfo.add(new TransactionInfo("Gaby", "Netflix", 50.00));
        Iterable<TransactionInfo> transactionInfolist = listTransactionInfo;

        when(userRepository.findByUsername(userEmail)).thenReturn(userTest);
        when(userRepository.getContactEmailListFromUserId(userTest.getUserId())).thenReturn(userEmailList);
        when(transactionRepository.getTransactionInfoListFromUser(userTest.getUserId())).thenReturn(transactionInfolist);

        //ACT
        TransferHomePageInfo transferHomePageInfo = transactionService.getTransferHomePageInfo(userEmail, PageRequest.of(0, 3));

        //ASSERT
        verify(userRepository, times(1)).findByUsername(userEmail);
        verify(userRepository, times(1)).getContactEmailListFromUserId(-1);
        verify(transactionRepository, times(1)).getTransactionInfoListFromUser(-1);
    }

}
