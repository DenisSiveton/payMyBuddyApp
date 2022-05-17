package com.payMyBuddy.AppMoneyTransfert.UnitTest.Controller;

import com.payMyBuddy.AppMoneyTransfert.AppMoneyTransferApplication;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.UserHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = AppMoneyTransferApplication.class)
@AutoConfigureMockMvc
public class UserControllerRequestTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void getHomePageTest() throws Exception {
        //ARRANGE
        String email = "j.d@hotmail.com";
        when(userService.getHomePage(email)).thenReturn(new UserHomePageInfo("Jean", 0, new ArrayList<>(), new ArrayList<>()));

        //ACT
        MvcResult result = mockMvc.perform(get("/home"))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        //ASSERT
        assertThat(content.equals("home"));
    }
}
