package com.payMyBuddy.AppMoneyTransfert.IntegrationTest.Repository;

import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.repository.ConnectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class ConnectionRepositoryIT {

    @Autowired
    ConnectionRepository connectionRepository;

    @Test
    public void getConnectionIdFromTwoUsers_ShouldReturnCorrectConnection(){
        //ARRANGE
        int senderId = 1;
        int receiverId = 2;

        //ACT
        Optional<Connection> result = connectionRepository.getConnectionIdFromTwoUsers(senderId, receiverId);

        //ASSERT
        assertThat(result.get().getConnectionId()).isEqualTo(1);
    }
    @Test
    public void getConnectionIdFromTwoUsers_ShouldReturnSameConnectionIfUserReversed(){
        //ARRANGE
        int senderId = 2;
        int receiverId = 1;

        //ACT
        Optional<Connection> result = connectionRepository.getConnectionIdFromTwoUsers(senderId, receiverId);

        //ASSERT
        assertThat(result.get().getConnectionId()).isEqualTo(1);
    }
}
