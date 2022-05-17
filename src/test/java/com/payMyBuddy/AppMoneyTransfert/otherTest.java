package com.payMyBuddy.AppMoneyTransfert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class otherTest {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    public void getEncodedPassWordFromRawPassword(){
        ArrayList<String> listRawPassword = new ArrayList<>(Arrays.asList("1234", "0000", "dauphin", "angeEtDemon", "voiture"));
        ArrayList<String> listEncodedPassword = new ArrayList<>();
        for (String rawPassword: listRawPassword
             ) {
            listEncodedPassword.add(encoder.encode(rawPassword));
        }
        assertThat(listRawPassword.size()).isEqualTo(5);
    }
}
