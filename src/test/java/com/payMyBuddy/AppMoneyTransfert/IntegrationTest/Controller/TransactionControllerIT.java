package com.payMyBuddy.AppMoneyTransfert.IntegrationTest.Controller;

import com.payMyBuddy.AppMoneyTransfert.AppMoneyTransferApplication;
import org.junit.jupiter.api.BeforeAll;
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
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/*@Import({MyUserDetailsService.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(ConnectionController.class)*/
@AutoConfigureMockMvc
@SpringBootTest(classes = AppMoneyTransferApplication.class)
public class TransactionControllerIT {

    @Autowired
    private MockMvc mvc;

    @BeforeAll
    public static void setUp(){

    }

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void addTransactionTest_ShouldEmitInsertCorrectData_CaseEmailIsMissing() throws Exception {
        Model model;
        String transactionReceiverEmail = "";
        String transactionDescription = "Test transaction";
        String transactionAmount = "5.50";
        String errorMessage = "Please insert valid email, amount and description for the transaction.";
        String keyValueModel = "listErrorMessage";

        MvcResult result = mvc.perform(get("/transaction")
                .param("email", transactionReceiverEmail)
                .param("amount", transactionAmount)
                .param("description", transactionDescription))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        String uriResult = resultModelAndView.getViewName();
        ArrayList errorMessageTest = (ArrayList) resultModelAndView.getModelMap().get(keyValueModel);


        assertTrue(uriResult.equals("transfer"));
        assertTrue(errorMessageTest.get(0).equals(errorMessage));
    }


    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void addTransactionTest_ShouldEmitInsertCorrectData_CaseAmountIsNegative() throws Exception {
        Model model;
        String transactionReceiverEmail = "m.m@hotmail.com";
        String transactionDescription = "Test transaction";
        String transactionAmount = "-5.50";
        String errorMessage = "Please insert valid email, amount and description for the transaction.";
        String keyValueModel = "listErrorMessage";

        MvcResult result = mvc.perform(get("/transaction")
                .param("email", transactionReceiverEmail)
                .param("amount", transactionAmount)
                .param("description", transactionDescription))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        String uriResult = resultModelAndView.getViewName();
        ArrayList errorMessageTest = (ArrayList) resultModelAndView.getModelMap().get(keyValueModel);


        assertTrue(uriResult.equals("transfer"));
        assertTrue(errorMessageTest.get(0).equals(errorMessage));
    }
    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void addTransactionTest_ShouldEmitInsertCorrectData_CaseDescriptionIsMissing() throws Exception {
        Model model;
        String transactionReceiverEmail = "m.m@hotmail.com";
        String transactionDescription = "";
        String transactionAmount = "5.50";
        String errorMessage = "Please insert valid email, amount and description for the transaction.";
        String keyValueModel = "listErrorMessage";

        MvcResult result = mvc.perform(get("/transaction")
                .param("email", transactionReceiverEmail)
                .param("amount", transactionAmount)
                .param("description", transactionDescription))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        String uriResult = resultModelAndView.getViewName();
        ArrayList errorMessageTest = (ArrayList) resultModelAndView.getModelMap().get(keyValueModel);


        assertTrue(uriResult.equals("transfer"));
        assertTrue(errorMessageTest.get(0).equals(errorMessage));
    }

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void addTransactionTest_ShouldEmitBalanceInsufficientException() throws Exception {
        Model model;
        String transactionReceiverEmail = "m.m@hotmail.com";
        String transactionDescription = "Test transaction";
        String transactionAmount = "550";
        String errorMessage = "Your balance account is not high enough to send this transaction." +
                "\n Please select a lower amount.";
        String keyValueModel = "listErrorMessage";

        MvcResult result = mvc.perform(get("/transaction")
                .param("email", transactionReceiverEmail)
                .param("amount", transactionAmount)
                .param("description", transactionDescription))
                .andReturn();
        ModelAndView resultModelAndView = result.getModelAndView();
        String uriResult = resultModelAndView.getViewName();
        ArrayList errorMessageTest = (ArrayList) resultModelAndView.getModelMap().get(keyValueModel);


        assertTrue(uriResult.equals("transfer"));
        assertTrue(errorMessageTest.get(0).equals(errorMessage));
    }
}
