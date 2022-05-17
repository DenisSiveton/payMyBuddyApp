package com.payMyBuddy.AppMoneyTransfert.UnitTest.Service;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import com.payMyBuddy.AppMoneyTransfert.model.DTO.UserInfo;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.UserHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.repository.TransactionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import com.payMyBuddy.AppMoneyTransfert.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(UserService.class)
public class UserServiceTest {

    @Autowired
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    TransactionRepository transactionRepository;

    static User userTest;

    @BeforeEach
    public void test_init(){
        userTest = new User("Harry", "Potter", "h.p@hotmail.com", "gryffindor", 100.00);

    }

    @Test
    public void getHomePageTest_seeIfCorrectMethodIsCalled(){
        //ARRANGE
        userTest.setUserId(0);
        String username = "j.d@hotmail.com";

        List<UserInfo> listUserInfo = new ArrayList<>();
        listUserInfo.add(new UserInfo("m.j@hotmail.com", "John", "Marc"));
        listUserInfo.add(new UserInfo("d.d@hotmail.com", "David", "Dash"));
        listUserInfo.add(new UserInfo("g.s@hotmail.com", "Gaby", "Sol"));
        Iterable<UserInfo> userInfolist = listUserInfo;

        List<TransactionInfo> listTransactionInfo = new ArrayList<>();
        listTransactionInfo.add(new TransactionInfo("John", "wedding", 50.00));
        listTransactionInfo.add(new TransactionInfo("John", "BDay", 50.00));
        listTransactionInfo.add(new TransactionInfo("Gaby", "Netflix", 50.00));
        Iterable<TransactionInfo> transactionInfolist = listTransactionInfo;

        when(userRepository.findByUsername(username)).thenReturn(userTest);

        when(userRepository.getRecentUsersInfo(userTest.getUserId())).thenReturn(userInfolist);
        when(transactionRepository.getRecentTransactionsInfo(userTest.getUserId())).thenReturn(transactionInfolist);

        //ACT
        UserHomePageInfo userHomePageInfo = userService.getHomePage(username);

        //ASSERT
        verify(userRepository, times(1)).getRecentUsersInfo(userTest.getUserId());
        verify(transactionRepository, times(1)).getRecentTransactionsInfo(userTest.getUserId());
    }
}
