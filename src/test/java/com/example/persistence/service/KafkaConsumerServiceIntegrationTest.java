package com.example.persistence.service;

import com.example.persistence.entity.EventTypeEntity;
import com.example.persistence.entity.SymbolEntity;
import com.example.persistence.entity.TradeEntity;
import com.example.persistence.entity.TickerEntity;
import com.example.persistence.repository.EventTypeRepository;
import com.example.persistence.repository.SymbolRepository;
import com.example.persistence.repository.TradeRepository;
import com.example.persistence.repository.TickerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = {"blockchain-trades", "blockchain-tickers"},
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        }
)
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092",
        "kafka.topics.blockchain-trades=blockchain-trades",
        "kafka.topics.blockchain-tickers=blockchain-tickers",
        "kafka.consumer.group-id=test-group"
})
@ActiveProfiles("test")
@DirtiesContext
public class KafkaConsumerServiceIntegrationTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TickerRepository tickerRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Test
    void shouldConsumeAndPersistTradeEvent() throws Exception {
        // Given
        String tradeEventJson = """
            {
                "eventId": "trade-001",
                "eventType": "trade",
                "source": "binance",
                "payload": {
                    "eventType": "trade",
                    "eventTimestamp": 1640995200000,
                    "symbol": "BTCUSDT",
                    "tradeId": 12345,
                    "price": "50000.00",
                    "quantity": "0.001",
                    "tradeTime": 1640995200000,
                    "isBuyerMarketMaker": true
                }
            }
            """;

        // When
        kafkaTemplate.send("blockchain-trades", tradeEventJson);

        // Then
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(tradeRepository.count()).isEqualTo(1);

            TradeEntity savedTrade = tradeRepository.findAll().get(0);
            assertThat(savedTrade.getPrice()).isEqualTo("50000.00");
            assertThat(savedTrade.getQuantity()).isEqualTo("0.001");
            assertThat(savedTrade.getTradeId()).isEqualTo(12345L);
            assertThat(savedTrade.getSymbol().getName()).isEqualTo("BTCUSDT");
            assertThat(savedTrade.getEventType().getType()).isEqualTo("trade");
        });
    }

    @Test
    void shouldConsumeAndPersistTickerEvent() throws Exception {
        // Given
        String tickerEventJson = """
            {
                "eventId": "ticker-001",
                "eventType": "24hrTicker",
                "source": "binance",
                "payload": {
                    "eventType": "24hrTicker",
                    "eventTimestamp": 1640995200000,
                    "symbol": "ETHUSDT",
                    "lastPrice": "4000.00",
                    "volume": "1000.50",
                    "priceChange": "100.00",
                    "priceChangePercent": "2.50"
                }
            }
            """;

        // When
        kafkaTemplate.send("blockchain-tickers", tickerEventJson);

        // Then
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(tickerRepository.count()).isEqualTo(1);

            TickerEntity savedTicker = tickerRepository.findAll().get(0);
            assertThat(savedTicker.getLastPrice()).isEqualTo("4000.00");
            assertThat(savedTicker.getPriceChange()).isEqualTo("100.00");
            assertThat(savedTicker.getSymbol().getName()).isEqualTo("ETHUSDT");
            assertThat(savedTicker.getEventType().getType()).isEqualTo("24hrTicker");
        });
    }

    @Test
    void shouldCreateSymbolAndEventTypeIfNotExists() throws Exception {
        // Given
        String tradeEventJson = """
            {
                "eventId": "trade-002",
                "eventType": "trade",
                "source": "binance",
                "payload": {
                    "eventType": "newTradeType",
                    "eventTimestamp": 1640995200000,
                    "symbol": "NEWCOIN",
                    "tradeId": 67890,
                    "price": "1.00",
                    "quantity": "100.0",
                    "tradeTime": 1640995200000,
                    "isBuyerMarketMaker": false
                }
            }
            """;

        // When
        kafkaTemplate.send("blockchain-trades", tradeEventJson);

        // Then
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(symbolRepository.count()).isEqualTo(1);
            assertThat(eventTypeRepository.count()).isEqualTo(1);

            SymbolEntity symbol = symbolRepository.findByName("NEWCOIN").get();
            EventTypeEntity eventType = eventTypeRepository.findByType("newTradeType").get();

            assertThat(symbol.getName()).isEqualTo("NEWCOIN");
            assertThat(eventType.getType()).isEqualTo("newTradeType");
        });
    }
}
