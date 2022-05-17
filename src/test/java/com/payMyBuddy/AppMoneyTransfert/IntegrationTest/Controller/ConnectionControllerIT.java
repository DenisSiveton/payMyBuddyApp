package com.payMyBuddy.AppMoneyTransfert.IntegrationTest.Controller;

import com.payMyBuddy.AppMoneyTransfert.AppMoneyTransferApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/*@Import({MyUserDetailsService.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(ConnectionController.class)*/
@AutoConfigureMockMvc
@SpringBootTest(classes = AppMoneyTransferApplication.class)
public class ConnectionControllerIT {

    @Autowired
    private MockMvc mvc;

    @BeforeAll
    public static void setUp(){

    }

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void addConnectionTest_ShouldEmitEmailIsNullError() throws Exception {
        Model model;
        String receiverEmail = "";
        String errorMessage = "Please insert an email.";
        String keyValueModel = "listErrorMessage";

        MvcResult result = mvc.perform(get("/connection/added")
                .param("receiverEmail", receiverEmail))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        ArrayList errorMessageTest = (ArrayList) resultModelAndView.getModelMap().get(keyValueModel);
        assertThat(resultModelAndView.getViewName().equals("addConnection"));
        assertThat(errorMessageTest.equals(errorMessage));
    }

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void addConnectionTest_ShouldEmitEmailIsSameAsUser() throws Exception {
        Model model;
        String receiverEmail = "j.d@hotmail.com";
        String errorMessage = "You can not create a Connection with yourself.";
        String keyValueModel = "listErrorMessage";

        MvcResult result = mvc.perform(get("/connection/added")
                .param("receiverEmail", receiverEmail))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        ArrayList errorMessageTest = (ArrayList) resultModelAndView.getModelMap().get(keyValueModel);
        assertThat(resultModelAndView.getViewName().equals("addConnection"));
        assertThat(errorMessageTest.equals(errorMessage));
    }

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void addConnectionTest_ShouldEmitEmailIsNotKnownToDatabase() throws Exception {
        Model model;
        String receiverEmail = "d.s@hotmail.com";
        String errorMessage = "The given email address does not match any user in our Database." +
                "Please try again with another email address.";
        String keyValueModel = "listErrorMessage";

        MvcResult result = mvc.perform(get("/connection/added")
                .param("receiverEmail", receiverEmail))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        ArrayList errorMessageTest = (ArrayList) resultModelAndView.getModelMap().get(keyValueModel);


        assertThat(resultModelAndView.getViewName().equals("addConnection"));
        assertThat(errorMessageTest.equals(errorMessage));
    }

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void addConnectionTest_ShouldEmitConnectionAlreadyExists() throws Exception {
        Model model;
        String receiverEmail = "m.m@hotmail.com";
        String errorMessage0 = "A connection already exists for the email address : " + receiverEmail + ".";
        String errorMessage1 = "Please try again with another email address.";
        String keyValueModel = "listErrorMessage";

        MvcResult result = mvc.perform(get("/connection/added")
                .param("receiverEmail", receiverEmail))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        ArrayList errorMessageTest = (ArrayList) resultModelAndView.getModelMap().get(keyValueModel);


        assertThat(resultModelAndView.getViewName().equals("addConnection"));
        assertThat(errorMessageTest.get(0).equals(errorMessage0));
        assertThat(errorMessageTest.get(1).equals(errorMessage1));
    }
    @Test
    @WithMockUser(username = "m.m@hotmail.com", password = "voiture", roles = "USER")
    public void addConnectionTest_ShouldAddConnection() throws Exception {
        Model model;
        String receiverEmail = "c.d@hotmail.com";
        String keyValueModel = "newConnectionEmail";

        MvcResult result = mvc.perform(get("/connection/added")
                .param("receiverEmail", receiverEmail))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        String newEmailConnection = (String) resultModelAndView.getModelMap().get(keyValueModel);


        assertThat(resultModelAndView.getViewName().equals("addedConnection"));
        assertThat(newEmailConnection.equals(receiverEmail));
    }
}
