package com.banking.account.queries.infrastructure.handlers;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {

    void on(AccountOpenedEvent openEvent);

    void on(AccountClosedEvent closeEvent);

    void on(FundsDepositedEvent depositEvent);

    void on(FundsWithdrawnEvent withdrawEvent);

}
