package com.example.persistence.service;

import com.example.persistence.entity.TradeEntity;
import com.example.persistence.exception.TradeNotFoundException;
import com.example.persistence.mapper.PayloadMapper;
import com.example.persistence.model.TradeDto;
import com.example.persistence.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    private static final Logger log = LoggerFactory.getLogger(TradeService.class);
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private PayloadMapper mapper;

    public TradeDto saveTrade(TradeDto dto) {
        TradeEntity trade = mapper.toTradeEntity(dto);
        trade = tradeRepository.save(trade);
        return mapper.toTradeDto(trade);
    }

    public TradeDto getTradeByTradeId(Long tradeId) {
        TradeEntity trade = tradeRepository.findByTradeId(tradeId)
                .orElseThrow(() -> new TradeNotFoundException("Trade with id " + tradeId + " not found"));
        return mapper.toTradeDto(trade);
    }

    public Page<TradeDto> getTradesBySymbol(String symbol, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<TradeEntity> trades = tradeRepository.findBySymbol(symbol, PageRequest.of(page, size, sort));
        return trades.map(mapper::toTradeDto);
    }

    public ResponseEntity deleteById(Long tradeId) {
        try {
            tradeRepository.deleteById(tradeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Cannot delete the trade: " + e.getMessage());
        }
    }

    public TradeDto updateTrade(TradeDto tradeDto) {
        return tradeRepository.findByTradeId(tradeDto.getTradeId()).map(
                existingTrade -> {
                    // Map updated fields from DTO to entity
                    TradeEntity updatedEntity = mapper.toTradeEntity(tradeDto);
                    updatedEntity.setId(existingTrade.getId()); // keep the same ID

                    TradeEntity saved = tradeRepository.save(updatedEntity);
                    return mapper.toTradeDto(saved);
                }).orElseThrow(() -> new TradeNotFoundException("Trade with id " + tradeDto.getTradeId() + " not found"));

    }
}
