package com.example.persistence.mapper;


import com.example.persistence.entity.TickerEntity;
import com.example.persistence.entity.TradeEntity;
import com.example.persistence.model.TickerDto;
import com.example.persistence.model.TradeDto;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface PayloadMapper {
    TradeEntity toTradeEntity(TradeDto dto);

    TradeDto toTradeDto(TradeEntity entity);

    TickerEntity toTickerEntity(TickerDto dto);

    TickerDto toTickerDto(TickerEntity entity);
}
