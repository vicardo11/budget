package it.sosinski.currencyexchangeservice.service;

import it.sosinski.aspectdirectory.logger.LogMethodAround;
import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeRequestDto;
import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeResponseDto;
import it.sosinski.currencyexchangeservice.repository.entity.CurrencyExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service responsible for calculating the currency exchange.
 */
@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {

    private final CurrencyCrudService currencyCrudService;

    /**
     * Get currency exchange based on given source currency, its amount and target currency.
     *
     * @param requestDto - requested currency exchange containing source currency, amount and target currency
     * @return - currency exchange
     */
    @LogMethodAround
    public CurrencyExchangeResponseDto getExchange(final CurrencyExchangeRequestDto requestDto) {
        final String fromCurrency = requestDto.getFromCurrency();
        final String toCurrency = requestDto.getToCurrency();
        final BigDecimal fromValue = requestDto.getFromValue();

        final CurrencyExchange currencyExchange = currencyCrudService.findCurrencyExchange(fromCurrency, toCurrency);
        final BigDecimal exchangeRate = currencyExchange.getExchangeRate();

        final BigDecimal toValue = calculateValue(fromValue, exchangeRate);
        return CurrencyExchangeResponseDto.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .fromValue(fromValue)
                .toValue(toValue)
                .build();
    }

    private BigDecimal calculateValue(final BigDecimal fromValue, final BigDecimal exchangeRate) {
        return fromValue.multiply(exchangeRate);
    }

}
