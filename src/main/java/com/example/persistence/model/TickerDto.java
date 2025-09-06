package com.example.persistence.model;

import lombok.Data;

@Data

public class TickerDto extends BaseDto {
    private Long tickerId;
    private String priceChange;
    private String priceChangePercent;
    private String weightedAveragePrice;
    private String firstTradeBeforeWindow;
    private String lastPrice;
    private String lastQuantity;
    private String bestBidPrice;
    private String bestBidQuantity;
    private String bestAskPrice;
    private String bestAskQuantity;
    private String openPrice;
    private String highPrice;
    private String lowPrice;
    private String totalTradedBaseAssetVolume;
    private String totalTradedQuoteAssetVolume;
    private Long statisticsOpenTime;
    private Long statisticsCloseTime;
    private Long firstTradeId;
    private Long lastTradeId;
    private Long totalTradeCount;
}
