package com.example.persistence.service;

import com.example.persistence.entity.EventTypeEntity;
import com.example.persistence.entity.SymbolEntity;
import com.example.persistence.repository.SymbolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SymbolService {

    private final SymbolRepository symbolRepository;

    public SymbolEntity findOrCreate(String symbol) {
        return symbolRepository.findByName(symbol)
                .orElseGet(() -> symbolRepository.save(new SymbolEntity(symbol)));
    }
}
