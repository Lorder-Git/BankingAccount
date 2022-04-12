package com.banking.account.queries.infrastructure.consumers;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumers {

    void consume(@Payload AccountOpenedEvent openedEvent, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent fundsDepositedEvent, Acknowledgment ack);
    void consume(@Payload FundsWithdrawnEvent fundsWithdrawnEvent, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent accountClosedEvent, Acknowledgment ack);

}
