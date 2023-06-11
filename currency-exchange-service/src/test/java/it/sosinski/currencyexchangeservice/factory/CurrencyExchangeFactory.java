package it.sosinski.currencyexchangeservice.factory;

import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeRequestDto;
import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeResponseDto;
import it.sosinski.currencyexchangeservice.dto.FreeCurrencyResponseDto;
import it.sosinski.currencyexchangeservice.repository.entity.CurrencyExchange;

import java.math.BigDecimal;
import java.util.Map;

public class CurrencyExchangeFactory {

    public static final BigDecimal VALUE_10 = BigDecimal.valueOf(10);
    public static final BigDecimal VALUE_15 = BigDecimal.valueOf(15);
    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final BigDecimal VALUE_1_15 = BigDecimal.valueOf(1.15);

    public static CurrencyExchange currencyExchange() {
        return CurrencyExchange.builder()
                .fromCurrency(USD)
                .toCurrency(EUR)
                .exchangeRate(VALUE_1_15)
                .build();
    }

    public static CurrencyExchangeRequestDto currencyExchangeRequestDto() {
        return CurrencyExchangeRequestDto.builder()
                .fromValue(VALUE_10)
                .fromCurrency(USD)
                .toCurrency(EUR)
                .build();
    }

    public static CurrencyExchangeResponseDto currencyExchangeResponseDto() {
        return CurrencyExchangeResponseDto.builder()
                .fromValue(VALUE_10)
                .fromCurrency(USD)
                .toValue(VALUE_15)
                .toCurrency(EUR)
                .build();
    }

    public static FreeCurrencyResponseDto freeCurrencyResponseDto() {
        return FreeCurrencyResponseDto.builder()
                .rates(Map.of(EUR, VALUE_1_15))
                .build();
    }

}
