package com.banking.account.cmd.api.command;

public interface CommandHandler {

    void handle(OpenAccountCommand openAccount);

    void handle(DepositFundsCommand depositMoney);

    void handle(WithdrawFundsCommand withdrawMoney);

    void handle(CloseAccountCommand closeAccount);

}
