package it.sosinski.currencyexchangeservice.service;

import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeRequestDto;
import it.sosinski.currencyexchangeservice.factory.CurrencyExchangeFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static it.sosinski.currencyexchangeservice.factory.CurrencyExchangeFactory.EUR;
import static it.sosinski.currencyexchangeservice.factory.CurrencyExchangeFactory.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeServiceTest {

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private CurrencyCrudService currencyCrudService;

    @ParameterizedTest
    @MethodSource("provideParameters")
    void shouldCalculateValueCorrectly(BigDecimal fromValue, BigDecimal expected) {
        // Given
        var currencyExchangeRequestDto = CurrencyExchangeRequestDto.builder()
                .fromCurrency(USD)
                .toCurrency(EUR)
                .fromValue(fromValue)
                .build();
        var currencyExchange = CurrencyExchangeFactory.currencyExchange();

        // When
        when(currencyCrudService.findCurrencyExchange(USD, EUR)).thenReturn(currencyExchange);
        var currencyExchangeResponseDto = currencyExchangeService.getExchange(currencyExchangeRequestDto);
        var actual = currencyExchangeResponseDto.getToValue();

        // Then
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(1), BigDecimal.valueOf(1.15)),
                Arguments.of(BigDecimal.valueOf(3), BigDecimal.valueOf(3.45)),
                Arguments.of(BigDecimal.valueOf(9), BigDecimal.valueOf(10.35)));
    }

}