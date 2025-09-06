package com.example.persistence.entity;

import lombok.Data;

import jakarta.persistence.*;


@Entity
@Table(name = "ticker")
@Data
public class TickerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @javax.persistence.JoinColumn(name = "event_type_id", nullable = false)
    private EventTypeEntity eventType;
    @Column(name = "event_timestamp")
    private Long eventTimestamp;
    @ManyToOne(optional = false)
    @javax.persistence.JoinColumn(name = "symbol_id", nullable = false)
    private SymbolEntity symbol;
    @Column(name = "price_change")
    private String priceChange;
    @Column(name = "price_change_percent")
    private String priceChangePercent;
    @Column(name = "weighted_average_price")
    private String weightedAveragePrice;
    @Column(name = "first_trade_before_window")
    private String firstTradeBeforeWindow;
    @Column(name = "last_price")
    private String lastPrice;
    @Column(name = "last_quantity")
    private String lastQuantity;
    @Column(name = "best_bid_price")
    private String bestBidPrice;
    @Column(name = "best_bid_quantity")
    private String bestBidQuantity;
    @Column(name = "best_ask_price")
    private String bestAskPrice;
    @Column(name = "best_ask_quantity")
    private String bestAskQuantity;
    @Column(name = "open_price")
    private String openPrice;
    @Column(name = "high_price")
    private String highPrice;
    @Column(name = "low_price")
    private String lowPrice;
    @Column(name = "total_traded_base_asset_volume")
    private String totalTradedBaseAssetVolume;
    @Column(name = "total_traded_quote_asset_volume")
    private String totalTradedQuoteAssetVolume;
    @Column(name = "statistics_open_time")
    private Long statisticsOpenTime;
    @Column(name = "statistics_close_time")
    private Long statisticsCloseTime;
    @Column(name = "first_trade_id")
    private Long firstTradeId;
    @Column(name = "last_trade_id")
    private Long lastTradeId;
    @Column(name = "total_trade_count")
    private Long totalTradeCount;
}
