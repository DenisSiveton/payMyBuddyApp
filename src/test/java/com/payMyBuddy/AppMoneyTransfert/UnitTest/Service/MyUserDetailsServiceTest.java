package com.payMyBuddy.AppMoneyTransfert.UnitTest.Service;

import com.payMyBuddy.AppMoneyTransfert.exception.DataNotFoundException;
import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import com.payMyBuddy.AppMoneyTransfert.model.DTO.UserInfo;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.UserHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.repository.TransactionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import com.payMyBuddy.AppMoneyTransfert.service.MyUserDetailsService;
import com.payMyBuddy.AppMoneyTransfert.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(MyUserDetailsService.class)
public class MyUserDetailsServiceTest {

    @Autowired
    MyUserDetailsService myUserDetailsService;
    @MockBean
    UserRepository userRepository;


    static User userTest;

    @BeforeEach
    public void test_init(){
        userTest = new User("Harry", "Potter", "h.p@hotmail.com", "gryffindor", 100.00);

    }

    @Test
    public void loadByUsername_seeIfCorrectMethodIsCalled(){
        //ARRANGE
        userTest.setUserId(0);
        String username = "h.p@hotmail.com";

        when(userRepository.findByUsername(username)).thenReturn(userTest);

        //ACT
        UserDetails user = myUserDetailsService.loadUserByUsername(username);

        //ASSERT
        verify(userRepository, times(1)).findByUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getPassword()).isEqualTo(userTest.getPassword());
    }

    @Test
    public void loadByUsername_seeIfUsernameNotFoundExceptionIsThrown(){
        //ARRANGE
        userTest.setUserId(0);
        String username = "h.p@hotmail.com";
        String exceptionMessage ="org.springframework.security.core.userdetails.UsernameNotFoundException: No user found with username: h.p@hotmail.com";

        when(userRepository.findByUsername(username)).thenReturn(null);

        //ACT
        Throwable exception = assertThrows(RuntimeException.class, () -> myUserDetailsService.loadUserByUsername(username));
        assertEquals(exceptionMessage, exception.getMessage());

        //ASSERT
        verify(userRepository, times(1)).findByUsername(username);
    }
}
