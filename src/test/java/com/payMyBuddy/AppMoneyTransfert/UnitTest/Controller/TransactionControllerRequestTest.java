package com.payMyBuddy.AppMoneyTransfert.UnitTest.Controller;

import com.payMyBuddy.AppMoneyTransfert.AppMoneyTransferApplication;
import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.TransferHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(
        classes = AppMoneyTransferApplication.class)

@AutoConfigureMockMvc
public class TransactionControllerRequestTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void getAllTransactionsFromUserTest() throws Exception {
        String email = "j.d@hotmail.com";
        int page = 1;
        Model model = null;
        when(transactionService.getTransferHomePageInfo(email, PageRequest.of(page, 3))).thenReturn(new TransferHomePageInfo());
        MvcResult result = mockMvc.perform(get("/transfer")
            .param("page", String.valueOf(Optional.of(1))))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        //ASSERT
        assertThat(content.equals("transfer"));
    }

    @Test
    @WithMockUser(username = "j.d@hotmail.com", password = "1234", roles = "USER")
    public void  addTransactionTest() throws Exception {
        String userEmail = "j.s@hotmail.com";
        String receiverEmail = "j.s@hotmail.com";
        double amountTransaction = 10;
        String descriptionTransaction = "test";
        Model model = null;
        //HttpServletRequest request, Model model, @Valid @RequestParam(value ="email") String receiverEmail
        //@Valid @RequestParam(value ="amount") double amount, @Valid @RequestParam(value ="description") String description
        when(transactionService.addTransaction(any(String.class), any(String.class), any(Double.class), any(String.class))).
                thenReturn(new Transaction(new Connection(), 1, 5, amountTransaction,"2021-01-01" , descriptionTransaction));
        MvcResult result = mockMvc.perform(get("/transaction")
                .param("email", receiverEmail)
                .param("amount", String.valueOf(amountTransaction))
                .param("description", descriptionTransaction))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        assertThat(content.equals("addedTransaction"));
    }
}
