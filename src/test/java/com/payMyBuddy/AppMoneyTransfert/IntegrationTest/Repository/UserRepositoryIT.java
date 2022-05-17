package com.payMyBuddy.AppMoneyTransfert.IntegrationTest.Repository;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.UserInfo;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;

    @Test
    public void getUserFromEmailTest_ShouldReturnCorrectUserInfo(){
        //ARRANGE
        String userEmailTest = "j.d@hotmail.com";
        //ACT
        User result = userRepository.findByUsername(userEmailTest);
        //ASSERT
        assertThat(result.getFirstName()).isEqualTo("Jean");
        assertThat(result.getUsername()).isEqualTo(userEmailTest);
    }

    @Test
    public void getUserFromEmailTest_ShouldReturnEmptyCauseNotFound(){
        //ARRANGE
        String userEmailTest = "sukan.s@hotmail.com";
        //ACT
        User result = userRepository.findByUsername(userEmailTest);
        //ASSERT
        assertThat(result).isNull();
    }

    @Test
    public void getUserBalanceFromIdTest_ShouldReturnCorrectBalance(){
        //ARRANGE
        int userIdTest = 3;
        //ACT
        Optional<Double> result = userRepository.getUserBalanceById(userIdTest);
        //ASSERT
        assertThat(result.get()).isEqualTo(75);
    }

    @Test
    public void getUserBalanceFromIdTest_ShouldReturnNoBalanceCauseNotFound(){
        //ARRANGE
        int userIdTest = 0;
        //ACT
        Optional<Double> result = userRepository.getUserBalanceById(userIdTest);
        //ASSERT
        assertThat(result.isEmpty());
    }

    @Test
    public void getEmailListFromIdTest_ShouldReturnCorrectEmailInfo(){
        //ARRANGE
        int userIdTest = 1;
        //ACT
        Iterable<String> result = userRepository.getContactEmailListFromUserId(userIdTest);
        List<String> listEmail = new ArrayList<>();
        result.forEach(listEmail::add);
        //ASSERT
        assertThat(listEmail.size()).isEqualTo(4);
        assertThat(listEmail.get(2)).isEqualTo("b.d@hotmail.com");
    }

    @Test
    public void getEmailListFromIdTest_ShouldReturnNoEmailCauseId0NotExist(){
        //ARRANGE
        int userIdTest = 0;
        //ACT
        Iterable<String> result = userRepository.getContactEmailListFromUserId(userIdTest);
        //ASSERT
        assertThat(result.iterator().hasNext()).isEqualTo(false);
    }

    @Test
    public void getRecentAddedUserInfoListFromIdTest_ShouldReturnCorrectUserInfo(){
        //ARRANGE
        int userIdTest =1;
        //ACT
        Iterable<UserInfo> result = userRepository.getRecentUsersInfo(userIdTest);
        List<UserInfo> listUserInfo = new ArrayList<>();
        result.forEach(listUserInfo::add);
        //ASSERT
        assertThat(listUserInfo.size()).isEqualTo(3);
        assertThat(listUserInfo.get(0).getFirstName()).isEqualTo("Maxime");
        assertThat(listUserInfo.get(2).getFirstName()).isEqualTo("Carole");
    }
    @Test
    public void getRecentAddedUserInfoListFromIdTest_ShouldReturnOnlyOneUserInfo(){
        //ARRANGE
        int userIdTest =2;
        //ACT
        Iterable<UserInfo> result = userRepository.getRecentUsersInfo(userIdTest);
        List<UserInfo> listUserInfo = new ArrayList<>();
        result.forEach(listUserInfo::add);
        //ASSERT
        assertThat(listUserInfo.size()).isEqualTo(1);
        assertThat(listUserInfo.get(0).getFirstName()).isEqualTo("Jean");
    }

    @Test
    public void updateUsersAccountAfterTransactionTest_ShouldUpdatedUsersBalance(){
        //ARRANGE
        int senderId = 1;
        double senderOldBalance = userRepository.getUserBalanceById(senderId).get();
        int receiverId = 2;
        double receiverOldBalance = userRepository.getUserBalanceById(receiverId).get();
        double amount = 10;

        //ACT
        userRepository.updateUsersAccountAfterTransaction(senderId, receiverId, amount);

        //ASSERT
        assertThat(userRepository.getUserBalanceById(senderId).get()).isEqualTo(Math.round((senderOldBalance - amount)*100.0)/100.0);
        assertThat(userRepository.getUserBalanceById(receiverId).get()).isEqualTo(Math.round((receiverOldBalance + amount)*100.0)/100.0);

    }
}
