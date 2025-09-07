package com.example.persistence.service;

import com.example.persistence.entity.EventTypeEntity;
import com.example.persistence.entity.SymbolEntity;
import com.example.persistence.entity.TickerEntity;
import com.example.persistence.mapper.PayloadMapper;
import com.example.persistence.model.TickerDto;
import com.example.persistence.repository.TickerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TickerServiceTest {
    @Mock
    private SymbolService symbolService;

    @Mock
    private EventTypeService eventTypeService;

    @Mock
    private TickerRepository tickerRepository;

    @Mock
    private PayloadMapper mapper;

    @InjectMocks
    private TickerService tickerService;

    private TickerDto tickerDto;
    private TickerEntity tickerEntity;
    private SymbolEntity symbolEntity;
    private EventTypeEntity eventTypeEntity;

    @BeforeEach
    void setUp() {
        // Setup test data
        tickerDto = new TickerDto();
        tickerDto.setSymbol("BTCUSDT");
        tickerDto.setEventType("24hrTicker");
        tickerDto.setLastPrice("50000.00");
        tickerDto.setEventTimestamp(System.currentTimeMillis());

        symbolEntity = new SymbolEntity("BTCUSDT");
        symbolEntity.setId(1);

        eventTypeEntity = new EventTypeEntity("24hrTicker");
        eventTypeEntity.setId(1);

        tickerEntity = new TickerEntity();
        tickerEntity.setId(1L);
        tickerEntity.setSymbol(symbolEntity);
        tickerEntity.setEventType(eventTypeEntity);
        tickerEntity.setLastPrice("50000.00");
    }

    @Test
    void shouldSaveTickerSuccessfully() {
        // Given
        when(symbolService.findOrCreate(anyString())).thenReturn(symbolEntity);
        when(eventTypeService.findOrCreate(anyString())).thenReturn(eventTypeEntity);
        when(mapper.toTickerEntity(any(TickerDto.class))).thenReturn(tickerEntity);
        when(tickerRepository.save(any(TickerEntity.class))).thenReturn(tickerEntity);
        when(mapper.toTickerDto(any(TickerEntity.class))).thenReturn(tickerDto);

        // When
        TickerDto result = tickerService.saveTicker(tickerDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getSymbol()).isEqualTo("BTCUSDT");
        assertThat(result.getEventType()).isEqualTo("24hrTicker");
        assertThat(result.getLastPrice()).isEqualTo("50000.00");

        verify(symbolService).findOrCreate("BTCUSDT");
        verify(eventTypeService).findOrCreate("24hrTicker");
        verify(tickerRepository).save(any(TickerEntity.class));
        verify(mapper).toTickerEntity(tickerDto);
        verify(mapper).toTickerDto(tickerEntity);
    }

    @Test
    void shouldSetSymbolAndEventTypeOnTickerEntity() {
        // Given
        TickerEntity mappedEntity = new TickerEntity();
        when(symbolService.findOrCreate(anyString())).thenReturn(symbolEntity);
        when(eventTypeService.findOrCreate(anyString())).thenReturn(eventTypeEntity);
        when(mapper.toTickerEntity(any(TickerDto.class))).thenReturn(mappedEntity);
        when(tickerRepository.save(any(TickerEntity.class))).thenReturn(tickerEntity);
        when(mapper.toTickerDto(any(TickerEntity.class))).thenReturn(tickerDto);

        // When
        tickerService.saveTicker(tickerDto);

        // Then
        verify(tickerRepository).save(argThat(entity ->
                entity.getSymbol().equals(symbolEntity) &&
                        entity.getEventType().equals(eventTypeEntity)
        ));
    }
}
