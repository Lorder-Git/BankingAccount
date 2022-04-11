package com.banking.account.cmd.domain;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.cmd.api.command.WithdrawFundsCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private String accountNumber;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private String customerCity;
    private String customerState;
    private String customerZip;
    private String customerCountry;
    private String customerType;
    private String accountType;
    private Boolean accountStatus;
    private String accountCurrency;
    private Double accountBalance;
    private String accountCreatedOn;
    private String accountLastUpdatedOn;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolderName(command.getAccountHolderName())
                .openingDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }
    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.accountStatus = true;
        this.accountBalance = event.getOpeningBalance();
    }

    public void depositFunds(Double amount) {
        if (!this.accountStatus) {
            throw new IllegalStateException("Account is closed, you cannot deposit funds");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }
    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.accountBalance += event.getAmount();
    }

    public void withdrawFunds(Double amount) {
        if (!this.accountStatus) {
            throw new IllegalStateException("Account is closed, you cannot withdraw funds");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }
    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.accountBalance -= event.getAmount();
    }

    public void closeAccount() {
        if (!this.accountStatus) {
            throw new IllegalStateException("Account is already closed");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }
    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.accountStatus = false;
    }

    @Override
    public void markChangesAsCommited() {
        this.accountLastUpdatedOn = new Date().toString();
    }

    public double getBalance() {
        return this.accountBalance;
    }
}
