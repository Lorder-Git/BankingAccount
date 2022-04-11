package com.banking.cqrs.core.events;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "eventsStore")
public class EventModel {

    @Id
    private String id;

    private String eventType;
    private String aggregateId;
    private String aggregateType;
    private Date timestamp;
    private Long version;
    private BaseEvent eventData;

}
