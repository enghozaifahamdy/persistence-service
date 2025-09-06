package com.example.persistence.controller;

import com.example.persistence.model.TickerDto;
import com.example.persistence.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/ticker")
public class TickerController {

    @Autowired
    private TickerService tickerService;

    @GetMapping(value = "/{tickerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TickerDto getTickerById(@PathVariable(value = "tickerId") Long tickerId) {
        return tickerService.getTickerById(tickerId);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TickerDto saveTicker(@RequestBody TickerDto tickerDto) {
        return tickerService.saveTicker(tickerDto);
    }

    @DeleteMapping(value = "/{tickerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<?> deleteTicker(@PathVariable(value = "tickerId") Long tickerId) {
        return tickerService.deleteById(tickerId);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    TickerDto updateTicker(@RequestBody TickerDto tickerDto) {
        return tickerService.updateTicker(tickerDto);
    }

    @GetMapping(value = "/symbol/{symbol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Page<TickerDto> getTickersBySymbol(@PathVariable(value = "symbol") String symbol,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size,
                                       @RequestParam(defaultValue = "id") String sortBy,
                                       @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return tickerService.getTickersBySymbol(symbol, page, size, sortBy, sortDirection);
    }
}
