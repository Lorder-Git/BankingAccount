package com.banking.account.cmd.api.command;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountCommandHandler implements CommandHandler {

    @Autowired
    private EventSourcingHandler<AccountAggregate> eventSourcing;

    @Override
    public void handle(OpenAccountCommand openAccount) {
        var aggregate = new AccountAggregate();
        eventSourcing.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand depositMoney) {
        var aggregate = eventSourcing.getById(depositMoney.getId());
        aggregate.depositFunds(depositMoney.getAmount());
        eventSourcing.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand withdrawMoney) {
        var aggregate = eventSourcing.getById(withdrawMoney.getId());
        if (aggregate.getBalance() < withdrawMoney.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        aggregate.withdrawFunds(withdrawMoney.getAmount());
    }

    @Override
    public void handle(CloseAccountCommand closeAccount) {
        var aggregate = eventSourcing.getById(closeAccount.getId());
        aggregate.closeAccount();
        eventSourcing.save(aggregate);
    }
}
