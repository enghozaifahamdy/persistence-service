package com.example.persistence.event;

import com.example.persistence.enums.EventTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockchainEvent {
    private String eventId;
    private String eventType;
    private String source;
    private Object payload;
}
