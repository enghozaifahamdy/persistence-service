package com.example.persistence.repository;

import com.example.persistence.entity.TickerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends JpaRepository<TickerEntity, Long> {
}
