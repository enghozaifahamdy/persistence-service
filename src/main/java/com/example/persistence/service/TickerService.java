package com.example.persistence.service;

import com.example.persistence.entity.EventTypeEntity;
import com.example.persistence.entity.SymbolEntity;
import com.example.persistence.entity.TickerEntity;
import com.example.persistence.mapper.PayloadMapper;
import com.example.persistence.model.TickerDto;
import com.example.persistence.repository.SymbolRepository;
import com.example.persistence.repository.TickerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TickerService {

    private SymbolService symbolService;
    private EventTypeService eventTypeService;
    private TickerRepository tickerRepository;
    private PayloadMapper mapper;

    public TickerDto saveTicker(TickerDto dto) {
        SymbolEntity symbol = symbolService.findOrCreate(dto.getSymbol());
        EventTypeEntity eventType = eventTypeService.findOrCreate(dto.getEventType());

        TickerEntity ticker = mapper.toTickerEntity(dto);
        ticker.setSymbol(symbol);
        ticker.setEventType(eventType);

        ticker = tickerRepository.save(ticker);
        return mapper.toTickerDto(ticker);
    }
}
