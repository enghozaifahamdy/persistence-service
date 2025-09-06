package com.example.persistence.repository;

import com.example.persistence.entity.EventTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventTypeRepository extends JpaRepository<EventTypeEntity, Long> {
    Optional<EventTypeEntity> findByType(String eventType);
}
