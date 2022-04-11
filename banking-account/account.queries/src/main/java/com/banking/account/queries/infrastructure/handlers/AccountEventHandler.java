package com.banking.account.queries.infrastructure.handlers;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.account.queries.domain.AccountRepository;
import com.banking.account.queries.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent openEvent) {
        var bankAccount =
                BankAccount.builder()
                        .accountId(openEvent.getId())
                        .accountHolder(openEvent.getAccountHolderName())
                        .accountCreatedDate(openEvent.getOpeningDate())
                        .accountType(openEvent.getAccountType())
                        .accountBalance(openEvent.getOpeningBalance())
                        .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(AccountClosedEvent closeEvent) {
        var bankAccount = accountRepository.findById(closeEvent.getId());
        bankAccount.ifPresent(accountRepository::delete);
    }

    @Override
    public void on(FundsDepositedEvent depositEvent) {
        var bankAccount = accountRepository.findById(depositEvent.getId());
        if (bankAccount.isPresent()) {
            bankAccount.get().getAccountBalance();
            return;
        }
        var currentBalance = bankAccount.get().getAccountBalance();
        var latestBalance = currentBalance + depositEvent.getAmount();
        bankAccount.get().setAccountBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawnEvent withdrawEvent) {
        var bankAccount = accountRepository.findById(withdrawEvent.getId());
        if (bankAccount.isPresent()) {
            bankAccount.get().getAccountBalance();
            return;
        }
        var currentBalance = bankAccount.get().getAccountBalance();
        var latestBalance = currentBalance - withdrawEvent.getAmount();
        bankAccount.get().setAccountBalance(latestBalance);
    }
}
