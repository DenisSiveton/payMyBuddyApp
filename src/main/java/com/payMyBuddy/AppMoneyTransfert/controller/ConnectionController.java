package com.payMyBuddy.AppMoneyTransfert.controller;

import com.payMyBuddy.AppMoneyTransfert.exception.BalanceInsufficientException;
import com.payMyBuddy.AppMoneyTransfert.exception.ConnectionRedundancyException;
import com.payMyBuddy.AppMoneyTransfert.exception.DataNotFoundException;
import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService){
        this.connectionService = connectionService;
    }


    @RequestMapping(value = "/connection/new", method = RequestMethod.GET)
    public String newConnectionForm() {
        return "addConnection";
    }

    @RequestMapping(value = "/connection/added", method = RequestMethod.GET)
    public String addConnection(Model model, @Valid @RequestParam(value = "receiverEmail") String receiverEmail) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(receiverEmail ==""){
            generateErrorMessage(model, new ArrayList<>(Arrays.asList("Please insert an email.")));
            return "addConnection";
        }else if(username.equals(receiverEmail)){
            generateErrorMessage(model, new ArrayList<>(Arrays.asList("You can not create a Connection with yourself.")));
            return "addConnection";
        }else{
            try {
                String newConnectionEmail = connectionService.addConnection(username, receiverEmail);
                model.addAttribute("newConnectionEmail", newConnectionEmail);
                return "addedConnection";
            } catch (DataNotFoundException exception) {
                generateErrorMessage(model, new ArrayList<>(Arrays.asList("The given email address does not match any user in our Database." +
                        "Please try again with another email address.")));
                return "addConnection";
            } catch (ConnectionRedundancyException exception) {
                generateErrorMessage(model, new ArrayList<>(Arrays.asList(
                        "A connection already exists for the email address : " + receiverEmail + ".",
                        "Please try again with another email address.")));
                return "addConnection";
            }
        }
    }

    private void generateErrorMessage(Model model, ArrayList<String> listError) {
        model.addAttribute("listErrorMessage", listError);
    }
}
