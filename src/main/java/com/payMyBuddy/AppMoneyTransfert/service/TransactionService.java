package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.exception.BalanceInsufficientException;
import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import com.payMyBuddy.AppMoneyTransfert.model.Transaction;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.model.viewModel.TransferHomePageInfo;
import com.payMyBuddy.AppMoneyTransfert.repository.ConnectionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.TransactionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.payMyBuddy.AppMoneyTransfert.configuration.tax.TaxTransactionConfig.TRANSACTION_TAX_PERCENTAGE;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

    public Transaction addTransaction(String username, String receiverEmail, double amountTransaction, String description) throws BalanceInsufficientException {
        User currentUser = userRepository.findByUsername(username);
        double balance = currentUser.getBalance();
        if(amountTransaction > balance){
            throw new BalanceInsufficientException("Your balance account is not high enough to send this transaction." +
                    "\n Please select an amount inferior to " + balance + "€.");
        }
        else {

            // Modify the amount of the transaction with the app tax and round it up to the hundredth.
            // The receiver will get 99.5% of the original transaction.
            double amountTransactionAfterTax = amountTransaction * (1 - TRANSACTION_TAX_PERCENTAGE / 100);
            amountTransactionAfterTax = Math.round((amountTransactionAfterTax) * 100.0) / 100.0;

            // Get the receiver Id
            int receiverId = userRepository.findByUsername(receiverEmail).getUserId();

            // Find the Connection between current user and Receiver Id
            Connection connection = connectionRepository.getConnectionIdFromTwoUsers(currentUser.getUserId(), receiverId).get();

            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnn", Locale.ENGLISH)).toString();

            // Create the Transaction with the gathered data
            Transaction transactionToAdd = new Transaction(connection, currentUser.getUserId(), receiverId, amountTransactionAfterTax, date, description);

            // Add the transaction to the DB
            Transaction addedTransaction = transactionRepository.save(transactionToAdd);

            // Update the balance of both users account
            userRepository.updateUsersAccountAfterTransaction(currentUser.getUserId(), receiverId, amountTransactionAfterTax);

            return addedTransaction;
        }
    }


    public TransferHomePageInfo getTransferHomePageInfo(String email, Pageable pageable){
        TransferHomePageInfo transferHomePageInfo = new TransferHomePageInfo();
        transferHomePageInfo.setListUserEmail(new ArrayList<>());

        User currentUser = userRepository.findByUsername(email);

        Iterable<String> listEmail = userRepository.getContactEmailListFromUserId(currentUser.getUserId());
        listEmail.forEach(transferHomePageInfo.getListUserEmail()::add);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Iterable<TransactionInfo> listTransactionInfoIterable = transactionRepository.getTransactionInfoListFromUser(currentUser.getUserId());
        List<TransactionInfo> listTransactionInfo = new ArrayList<>();

        listTransactionInfoIterable.forEach(listTransactionInfo::add);

        List<TransactionInfo> listTransactionInfoPerPage;
        if (listTransactionInfo.size() < startItem) {
            listTransactionInfoPerPage = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listTransactionInfo.size());
            listTransactionInfoPerPage = listTransactionInfo.subList(startItem, toIndex);
        }
        transferHomePageInfo.setListTransactionInfo(new PageImpl<TransactionInfo>(listTransactionInfoPerPage, PageRequest.of(currentPage, pageSize), listTransactionInfo.size()));
        return transferHomePageInfo;
    }
}
