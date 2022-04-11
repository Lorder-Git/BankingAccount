package com.banking.account.cmd.infrastructure;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.cqrs.core.domain.AggregateRoot;
import com.banking.cqrs.core.handlers.EventSourcingHandler;
import com.banking.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.save(aggregateRoot.getId(), aggregateRoot.getUncommitedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommited();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream()
                    .map(e -> e.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }
}
