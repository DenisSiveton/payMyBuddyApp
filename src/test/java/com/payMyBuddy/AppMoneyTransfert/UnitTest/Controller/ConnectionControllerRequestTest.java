package com.payMyBuddy.AppMoneyTransfert.UnitTest.Controller;

import com.payMyBuddy.AppMoneyTransfert.AppMoneyTransferApplication;
import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.repository.TransactionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import com.payMyBuddy.AppMoneyTransfert.service.ConnectionService;
import com.payMyBuddy.AppMoneyTransfert.service.MyUserDetailsService;
import com.payMyBuddy.AppMoneyTransfert.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@Import({MyUserDetailsService.class})
//@WebMvcTest
//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppMoneyTransferApplication.class)

@AutoConfigureMockMvc
public class ConnectionControllerRequestTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConnectionService connectionService;

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void newConnectionFormTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/connection/new"))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        //ASSERT
        assertThat(content.equals("addConnection"));
    }

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void  addConnectionTest() throws Exception {
        String receiverEmail = "j.s@hotmail.com";
        Model model = null;
        MvcResult result = mockMvc.perform(get("/connection/added")
                .param("email", receiverEmail))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        assertThat(content.equals("addedConnection"));
    }
}
