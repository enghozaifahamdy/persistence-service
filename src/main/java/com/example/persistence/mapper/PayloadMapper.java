package com.example.persistence.mapper;


import com.example.persistence.entity.TickerEntity;
import com.example.persistence.entity.TradeEntity;
import com.example.persistence.model.TickerDto;
import com.example.persistence.model.TradeDto;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface PayloadMapper {
    @Mapping(target = "eventType", ignore = true)
    @Mapping(target = "symbol", ignore = true)
    TradeEntity toTradeEntity(TradeDto dto);

    @Mapping(target = "eventType", ignore = true)
    @Mapping(target = "symbol", ignore = true)
    TradeDto toTradeDto(TradeEntity entity);

    @Mapping(target = "eventType", ignore = true)
    @Mapping(target = "symbol", ignore = true)
    TickerEntity toTickerEntity(TickerDto dto);

    @Mapping(target = "eventType", ignore = true)
    @Mapping(target = "symbol", ignore = true)
    TickerDto toTickerDto(TickerEntity entity);
}
