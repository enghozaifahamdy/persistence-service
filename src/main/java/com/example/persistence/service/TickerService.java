package com.example.persistence.service;

import com.example.persistence.entity.TickerEntity;
import com.example.persistence.exception.TickerNotFoundException;
import com.example.persistence.mapper.PayloadMapper;
import com.example.persistence.model.TickerDto;
import com.example.persistence.repository.TickerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
@Service
public class TickerService {
    private static final Logger log = LoggerFactory.getLogger(TickerService.class);
    @Autowired
    private TickerRepository tickerRepository;
    @Autowired
    private PayloadMapper mapper;

    public TickerDto saveTicker(TickerDto dto) {
        TickerEntity ticker = mapper.toTickerEntity(dto);
        ticker = tickerRepository.save(ticker);
        return mapper.toTickerDto(ticker);
    }

    public TickerDto getTickerById(Long tickerId) {
        TickerEntity ticker = tickerRepository.findById(tickerId)
                .orElseThrow(() -> new TickerNotFoundException("Ticker with id " + tickerId + " not found"));
        return mapper.toTickerDto(ticker);
    }

    public Page<TickerDto> getTickersBySymbol(String symbol, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Page<TickerEntity> tickers = tickerRepository.findBySymbol(symbol, PageRequest.of(page, size, sort));
        return tickers.map(mapper::toTickerDto);
    }

    public ResponseEntity<?> deleteById(Long tickerId) {
        try {
            tickerRepository.deleteById(tickerId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Cannot delete the ticker: " + e.getMessage());
        }
    }

    public TickerDto updateTicker(TickerDto tickerDto) {
        return tickerRepository.findById(tickerDto.getTickerId()).map(
                existingTicker -> {
                    TickerEntity updatedEntity = mapper.toTickerEntity(tickerDto);
                    updatedEntity.setId(existingTicker.getId()); // preserve ID

                    TickerEntity saved = tickerRepository.save(updatedEntity);
                    return mapper.toTickerDto(saved);
                }).orElseThrow(() ->
                new TickerNotFoundException("Ticker with id " + tickerDto.getTickerId() + " not found")
        );
    }
}
