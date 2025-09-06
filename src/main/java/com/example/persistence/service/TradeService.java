package com.example.persistence.service;

import com.example.persistence.entity.EventTypeEntity;
import com.example.persistence.entity.SymbolEntity;
import com.example.persistence.entity.TradeEntity;
import com.example.persistence.mapper.PayloadMapper;
import com.example.persistence.model.TradeDto;
import com.example.persistence.repository.TradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TradeService {

    private SymbolService symbolService;
    private EventTypeService eventTypeService;
    private TradeRepository tradeRepository;
    private PayloadMapper mapper;

    public TradeDto saveTrade(TradeDto dto) {
        SymbolEntity symbol = symbolService.findOrCreate(dto.getSymbol());
        EventTypeEntity eventType = eventTypeService.findOrCreate(dto.getEventType());

        TradeEntity trade = mapper.toTradeEntity(dto);
        trade.setSymbol(symbol);
        trade.setEventType(eventType);

        trade = tradeRepository.save(trade);
        return mapper.toTradeDto(trade);
    }


}
