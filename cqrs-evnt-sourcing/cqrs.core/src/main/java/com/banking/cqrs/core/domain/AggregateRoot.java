package com.banking.cqrs.core.domain;

import com.banking.cqrs.core.events.BaseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AggregateRoot {

    protected String id;

    private Long version = 1L;

    private final List<BaseEvent> changes = new ArrayList<>();

    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommitedChanges() {
        return this.changes;
    }

    public void clearCommitedChanges() {
        this.changes.clear();
    }

    protected void applyChanges(BaseEvent event, Boolean isNewEvent) {
        try {
            var method = this.getClass().getMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException nsme) {
            logger.warning("No apply method found for event to {0}" + event.getClass().getName());
        } catch (Exception e) {
                logger.warning("Error applying event to aggregate" + event.getClass().getName());
        }finally {
            if (isNewEvent) {
                logger.info("Applying event " + event.getClass().getName());
                this.changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        this.applyChanges(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(evnt -> this.applyChanges(evnt, false));
    }

    public abstract void markChangesAsCommited();
}
