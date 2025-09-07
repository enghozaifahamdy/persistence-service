package com.example.persistence.service;

import com.example.persistence.entity.EventTypeEntity;
import com.example.persistence.entity.SymbolEntity;
import com.example.persistence.entity.TradeEntity;
import com.example.persistence.mapper.PayloadMapper;
import com.example.persistence.model.TradeDto;
import com.example.persistence.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {
    @Mock
    private SymbolService symbolService;

    @Mock
    private EventTypeService eventTypeService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private PayloadMapper mapper;

    @InjectMocks
    private TradeService tradeService;

    private TradeDto tradeDto;
    private TradeEntity tradeEntity;
    private SymbolEntity symbolEntity;
    private EventTypeEntity eventTypeEntity;

    @BeforeEach
    void setUp() {
        tradeDto = new TradeDto();
        tradeDto.setSymbol("ETHUSDT");
        tradeDto.setEventType("trade");
        tradeDto.setPrice("3000.50");
        tradeDto.setQuantity("0.5");
        tradeDto.setTradeId(12345L);
        tradeDto.setIsBuyerMarketMaker(true);
        tradeDto.setTradeTime(System.currentTimeMillis());

        symbolEntity = new SymbolEntity("ETHUSDT");
        symbolEntity.setId(2);

        eventTypeEntity = new EventTypeEntity("trade");
        eventTypeEntity.setId(2);

        tradeEntity = new TradeEntity();
        tradeEntity.setId(1L);
        tradeEntity.setSymbol(symbolEntity);
        tradeEntity.setEventType(eventTypeEntity);
        tradeEntity.setPrice("3000.50");
        tradeEntity.setQuantity("0.5");
        tradeEntity.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldSaveTradeSuccessfully() {
        // Given
        when(symbolService.findOrCreate(anyString())).thenReturn(symbolEntity);
        when(eventTypeService.findOrCreate(anyString())).thenReturn(eventTypeEntity);
        when(mapper.toTradeEntity(any(TradeDto.class))).thenReturn(tradeEntity);
        when(tradeRepository.save(any(TradeEntity.class))).thenReturn(tradeEntity);
        when(mapper.toTradeDto(any(TradeEntity.class))).thenReturn(tradeDto);

        // When
        TradeDto result = tradeService.saveTrade(tradeDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getSymbol()).isEqualTo("ETHUSDT");
        assertThat(result.getEventType()).isEqualTo("trade");
        assertThat(result.getPrice()).isEqualTo("3000.50");
        assertThat(result.getQuantity()).isEqualTo("0.5");

        verify(symbolService).findOrCreate("ETHUSDT");
        verify(eventTypeService).findOrCreate("trade");
        verify(tradeRepository).save(any(TradeEntity.class));
    }

    @Test
    void shouldHandleBuyerMarketMakerFlag() {
        // Given
        tradeDto.setIsBuyerMarketMaker(false);
        when(symbolService.findOrCreate(anyString())).thenReturn(symbolEntity);
        when(eventTypeService.findOrCreate(anyString())).thenReturn(eventTypeEntity);
        when(mapper.toTradeEntity(any(TradeDto.class))).thenReturn(tradeEntity);
        when(tradeRepository.save(any(TradeEntity.class))).thenReturn(tradeEntity);
        when(mapper.toTradeDto(any(TradeEntity.class))).thenReturn(tradeDto);

        // When
        TradeDto result = tradeService.saveTrade(tradeDto);

        // Then
        assertThat(result.getIsBuyerMarketMaker()).isFalse();
    }
}
