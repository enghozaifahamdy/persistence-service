package com.example.persistence.service;

import com.example.persistence.event.BlockchainEvent;
import com.example.persistence.model.TickerDto;
import com.example.persistence.model.TradeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private TradeService tradeService;
    private TickerService tickerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${kafka.topics.blockchain-trades}",
            groupId = "${kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeTradeEvents(String message) {
        try {

            BlockchainEvent event = objectMapper.readValue(message, BlockchainEvent.class);
            tradeService.saveTrade(objectMapper.convertValue(event.getPayload(), TradeDto.class));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to process trade event: {} | Message: {}", e.getMessage(), message);
        }
    }

    @KafkaListener(topics = "${kafka.topics.blockchain-tickers}",
            groupId = "${kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeTickerEvents(String message) {
        try {

            BlockchainEvent event = objectMapper.readValue(message, BlockchainEvent.class);
            tickerService.saveTicker(objectMapper.convertValue(event.getPayload(), TickerDto.class));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to process ticker event: {} | Message: {}", e.getMessage(), message);
        }
    }
}
