package com.example.persistence.service;

import com.example.persistence.entity.EventTypeEntity;
import com.example.persistence.entity.SymbolEntity;
import com.example.persistence.repository.EventTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventTypeService {

    private final EventTypeRepository eventTypeRepository;

    EventTypeEntity findOrCreate(String eventType) {

        return eventTypeRepository.findByType(eventType)
                .orElseGet(() -> eventTypeRepository.save(new EventTypeEntity(eventType)));
    }
}
