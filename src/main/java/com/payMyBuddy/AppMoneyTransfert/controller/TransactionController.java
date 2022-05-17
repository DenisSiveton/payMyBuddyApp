package com.payMyBuddy.AppMoneyTransfert.controller;

import com.payMyBuddy.AppMoneyTransfert.exception.BalanceInsufficientException;
import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.TransferHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }


    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public String getAllTransactionsFromUser(Model model, @RequestParam(value = "page") Optional<Integer> page) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        int currentPage = page.orElse(1);
        TransferHomePageInfo result =transactionService.getTransferHomePageInfo(username, PageRequest.of(currentPage -1, 3));
        model.addAttribute("listTransactionHomeInfo", result);

        int totalPages = result.getListTransactionInfo().getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "transfer";
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String addTransaction(HttpServletRequest request, Model model, @Valid @RequestParam(value ="email") String receiverEmail, @Valid @RequestParam(value ="amount") double amount, @Valid @RequestParam(value ="description") String description) throws BalanceInsufficientException {
        if (receiverEmail.isEmpty() || amount <= 0 || description.isEmpty() ){
            generateErrorMessage(model, new ArrayList<>(Arrays.asList("Please insert valid email, amount and description for the transaction.")));
            return getAllTransactionsFromUser(model, Optional.of(1));
        }
        else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            try {
                Transaction newTransaction = transactionService.addTransaction(username, receiverEmail, amount, description);
                model.addAttribute("transactionAdded", newTransaction);
                return "addedTransaction";
            } catch (BalanceInsufficientException exception) {
                generateErrorMessage(model, new ArrayList<>(Arrays.asList("Your balance account is not high enough to send this transaction." +
                        "\n Please select a lower amount.")));
                return getAllTransactionsFromUser(model, Optional.of(1));
            }
        }
    }
    private void generateErrorMessage(Model model, ArrayList<String> listError) {
        model.addAttribute("listErrorMessage", listError);
    }
}