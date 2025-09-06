package com.example.persistence.controller;

import com.example.persistence.model.TradeDto;
import com.example.persistence.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/trade")
public class TradeController {
    @Autowired
    private TradeService tradeService;

    @GetMapping(value = "/{tradeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TradeDto getTradeByTradeId(@PathVariable(value = "tradeId") Long tradeId) {
        return tradeService.getTradeByTradeId(tradeId);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TradeDto saveTrade(@RequestBody TradeDto tradeDto) {
        return tradeService.saveTrade(tradeDto);
    }

    @DeleteMapping(value = "/{tradeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity deleteTrade(@PathVariable(value = "tradeId") Long tradeId) {
        return tradeService.deleteById(tradeId);
    }

    @PutMapping("")
    public TradeDto updateTrade(@RequestBody TradeDto tradeDto) {
        return tradeService.updateTrade(tradeDto);
    }

    @GetMapping(value = "/symbol/{symbol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Page<TradeDto> getTradesBySymbol(@PathVariable(value = "symbol") String symbol,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size,
                                     @RequestParam(defaultValue = "tradeId") String sortBy,
                                     @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return tradeService.getTradesBySymbol(symbol, page, size, sortBy, sortDirection);
    }
}
