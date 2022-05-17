package com.payMyBuddy.AppMoneyTransfert.UnitTest.Service;

import com.payMyBuddy.AppMoneyTransfert.exception.BalanceInsufficientException;
import com.payMyBuddy.AppMoneyTransfert.exception.ConnectionRedundancyException;
import com.payMyBuddy.AppMoneyTransfert.exception.DataNotFoundException;
import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.TransferHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.repository.ConnectionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.TransactionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import com.payMyBuddy.AppMoneyTransfert.service.ConnectionService;
import com.payMyBuddy.AppMoneyTransfert.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(ConnectionService.class)
public class ConnectionServiceTest {
    @Autowired
    ConnectionService connectionService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    ConnectionRepository connectionRepository;

    static Connection connectionTest;
    static User userTest;
    static User receiverTest;

    @BeforeEach
    public void test_init(){
        connectionTest = new Connection();
        userTest = new User("Jean", "Durand", "j.d@hotmail.com", "1234", 100.00);
        userTest.setUserId(-1);
        receiverTest = new User("Harry", "Potter", "h.p@hotmail.com", "gryffindor", 100.00);
        receiverTest.setUserId(0);

    }

    @Test
    public void addConnectionTest_seeIfCorrectMethodIsCalled() throws ConnectionRedundancyException, DataNotFoundException {
        //ARRANGE
        String usernameTest = "j.d@hotmail.com";
        String emailTest = "h.p@hotmail.com";

        when(userRepository.findByUsername(emailTest)).thenReturn(receiverTest);
        when(userRepository.findByUsername(usernameTest)).thenReturn(userTest);
        when(connectionRepository.doesConnectionAlreadyExists(userTest.getUserId(), receiverTest.getUserId())).thenReturn(null);

        //ACT
        String newUserEmail = connectionService.addConnection(usernameTest, emailTest);

        //ASSERT
        verify(userRepository, times(1)).findByUsername(emailTest);
        verify(userRepository, times(1)).findByUsername(usernameTest);
        verify(connectionRepository, times(1)).doesConnectionAlreadyExists(userTest.getUserId(), receiverTest.getUserId());
    }

    @Test
    public void addConnectionTest_DataNotFoundExceptionShouldBeThrown() throws ConnectionRedundancyException, DataNotFoundException {
        //ARRANGE
        String usernameTest = "j.d@hotmail.com";
        String emailTest = "h.p@hotmail.com";
        String exceptionMessage = "The given email address does not match any user in our Database. Please try again with another email address";

        when(userRepository.findByUsername(emailTest)).thenReturn(null);


        //ACT
        Throwable exception = assertThrows(DataNotFoundException.class, () -> connectionService.addConnection(usernameTest, emailTest));
        assertEquals(exceptionMessage, exception.getMessage());

        //ASSERT
        verify(userRepository, times(1)).findByUsername(emailTest);
    }

    @Test
    public void addConnectionTest_ConnectionRedundancyExceptionShouldBeThrown() throws ConnectionRedundancyException, DataNotFoundException {
        //ARRANGE
        String usernameTest = "j.d@hotmail.com";
        String emailTest = "h.p@hotmail.com";
        String exceptionMessage = "A connection already exists for this email address. Please try again with another email address";

        when(userRepository.findByUsername(emailTest)).thenReturn(receiverTest);
        when(userRepository.findByUsername(usernameTest)).thenReturn(userTest);
        when(connectionRepository.doesConnectionAlreadyExists(userTest.getUserId(), receiverTest.getUserId())).thenReturn(1);

        //ACT
        Throwable exception = assertThrows(ConnectionRedundancyException.class, () -> connectionService.addConnection(usernameTest, emailTest));
        assertEquals(exceptionMessage, exception.getMessage());

        //ASSERT
        verify(userRepository, times(1)).findByUsername(emailTest);
        verify(userRepository, times(1)).findByUsername(usernameTest);
        verify(connectionRepository, times(1)).doesConnectionAlreadyExists(userTest.getUserId(), receiverTest.getUserId());
    }
}
