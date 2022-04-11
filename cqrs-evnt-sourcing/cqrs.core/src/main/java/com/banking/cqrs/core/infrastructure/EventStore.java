package com.banking.cqrs.core.infrastructure;

import com.banking.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void save(String aggregateId, Iterable<BaseEvent> event, Long expectedVersion);

    List<BaseEvent> getEvents(String aggregateId);

}
