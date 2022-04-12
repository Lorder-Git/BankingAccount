package com.banking.account.queries.infrastructure.consumers;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.account.queries.infrastructure.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumers{

    @Autowired
    private EventHandler eventHandler;

    @KafkaListener(topics = "accountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountOpenedEvent openedEvent, Acknowledgment ack) {
        eventHandler.on(openedEvent);
        ack.acknowledge();
    }

    @KafkaListener(topics = "fundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsDepositedEvent fundsDepositedEvent, Acknowledgment ack) {
        eventHandler.on(fundsDepositedEvent);
        ack.acknowledge();
    }

    @KafkaListener(topics = "fundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsWithdrawnEvent fundsWithdrawnEvent, Acknowledgment ack) {
        eventHandler.on(fundsWithdrawnEvent);
        ack.acknowledge();
    }

    @KafkaListener(topics = "accountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountClosedEvent accountClosedEvent, Acknowledgment ack) {
        eventHandler.on(accountClosedEvent);
        ack.acknowledge();
    }
}
