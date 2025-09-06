package com.example.persistence.model;

import lombok.Data;

@Data
public class BaseDto {
    String eventType;
    Long eventTimestamp;
    String symbol;
}
