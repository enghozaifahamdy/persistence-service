package com.example.persistence.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade")
@Data
public class TradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "event_type")
    private String eventType;
    @Column(name = "event_timestamp")
    private Long eventTimestamp;
    private String symbol;
    @Column(name = "trade_id")
    private Long tradeId;
    private String price;
    private String quantity;
    @Column(name = "trade_time")
    private Long tradeTime;
    @Column(name = "is_buyer_market_maker")
    private Boolean isBuyerMarketMaker;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
