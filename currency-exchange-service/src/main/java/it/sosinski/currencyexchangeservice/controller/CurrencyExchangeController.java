package it.sosinski.currencyexchangeservice.controller;

import it.sosinski.aspectdirectory.logger.LogMethodAround;
import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeRequestDto;
import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeResponseDto;
import it.sosinski.currencyexchangeservice.service.CurrencyExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.sosinski.currencyexchangeservice.configuration.UriConstants.URI_CURRENCY_EXCHANGE;

@RestController
@RequestMapping(URI_CURRENCY_EXCHANGE)
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    /**
     * Get currency exchange based on given source currency, its amount and target currency.
     *
     * @param currencyExchangeRequestDto - requested currency exchange containing source currency, amount and target currency
     * @return - currency exchange
     */
    @GetMapping
    @LogMethodAround
    public ResponseEntity<CurrencyExchangeResponseDto> getExchange(@RequestBody @Valid final CurrencyExchangeRequestDto currencyExchangeRequestDto) {
        final CurrencyExchangeResponseDto currencyExchangeResponseDto = currencyExchangeService.getExchange(currencyExchangeRequestDto);
        return new ResponseEntity<>(currencyExchangeResponseDto, HttpStatus.OK);
    }

}
