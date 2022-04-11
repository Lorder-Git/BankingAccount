package com.banking.account.cmd.infrastructure;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.account.cmd.domain.EventStoreRepository;
import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.events.EventModel;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.exceptions.ConcurrencyExceptions;
import com.banking.cqrs.core.infrastructure.EventStore;
import com.banking.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository accountEventRepository;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public void save(String aggregateId, Iterable<BaseEvent> event, Long expectedVersion) {
        var eventStream = accountEventRepository.findByAggregateId(aggregateId);
        if (eventStream != null && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyExceptions("Concurrency conflict");
        }
        var version = expectedVersion;
        for (var evnt : event) {
            version++;
            evnt.setVersion(version);
            var eventModel = EventModel.builder()
                    .aggregateId(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(evnt.getClass().getSimpleName())
                    .eventData(evnt)
                    .build();

            var persistedEvent = accountEventRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                // produce Event to Kafka
                eventProducer.produce(event.getClass().getSimpleName(), evnt);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = accountEventRepository.findByAggregateId(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("No events found for aggregateId: " + aggregateId);
        }
        return eventStream.stream()
                .map(EventModel::getEventData)
                .map(BaseEvent.class::cast)
                .collect(Collectors.toList());
    }
}
