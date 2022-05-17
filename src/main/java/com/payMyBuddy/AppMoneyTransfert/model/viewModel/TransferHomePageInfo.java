package com.payMyBuddy.AppMoneyTransfert.model.viewModel;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.TransactionInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public class TransferHomePageInfo {

    private List<String> listUserEmail;

    private Page<TransactionInfo> listTransactionInfo;

    public TransferHomePageInfo() {
    }

    public List<String> getListUserEmail() {
        return listUserEmail;
    }

    public void setListUserEmail(List<String> listUserEmail) {
        this.listUserEmail = listUserEmail;
    }

    public Page<TransactionInfo> getListTransactionInfo() {
        return listTransactionInfo;
    }

    public void setListTransactionInfo(Page<TransactionInfo> listTransactionInfo) {
        this.listTransactionInfo = listTransactionInfo;
    }
}
