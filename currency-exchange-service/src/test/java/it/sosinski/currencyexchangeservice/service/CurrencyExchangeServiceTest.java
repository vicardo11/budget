package it.sosinski.currencyexchangeservice.service;

import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeRequestDto;
import it.sosinski.currencyexchangeservice.factory.CurrencyExchangeFactory;
import it.sosinski.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static it.sosinski.currencyexchangeservice.factory.CurrencyExchangeFactory.EUR;
import static it.sosinski.currencyexchangeservice.factory.CurrencyExchangeFactory.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeServiceTest {

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Mock
    private FreeCurrencyService freeCurrencyService;

    @Test
    void shouldCallFreeCurrencyApiWhenNotFoundInRepository() {
        // Given
        var currencyExchangeRequestDto = CurrencyExchangeFactory.currencyExchangeRequestDto();
        var freeCurrencyResponseDto = CurrencyExchangeFactory.freeCurrencyResponseDto();

        // When
        when(currencyExchangeRepository.findByFromCurrencyAndToCurrency(USD, EUR)).thenReturn(Optional.empty());
        when(freeCurrencyService.getCurrencyFromApi(USD)).thenReturn(freeCurrencyResponseDto);
        currencyExchangeService.getExchange(currencyExchangeRequestDto);

        // Then
        verify(freeCurrencyService).getCurrencyFromApi(any());
    }

    @Test
    void shouldSaveToRepositoryWhenNotFoundEarlier() {
        // Given
        var currencyExchangeRequestDto = CurrencyExchangeFactory.currencyExchangeRequestDto();
        var freeCurrencyResponseDto = CurrencyExchangeFactory.freeCurrencyResponseDto();

        // When
        when(currencyExchangeRepository.findByFromCurrencyAndToCurrency(USD, EUR)).thenReturn(Optional.empty());
        when(freeCurrencyService.getCurrencyFromApi(USD)).thenReturn(freeCurrencyResponseDto);
        currencyExchangeService.getExchange(currencyExchangeRequestDto);

        // Then
        verify(currencyExchangeRepository).save(any());
    }

    @Test
    void shouldNotCallFreeCurrencyApiWhenFoundInRepository() {
        // Given
        var currencyExchangeRequestDto = CurrencyExchangeFactory.currencyExchangeRequestDto();
        var currencyExchange = CurrencyExchangeFactory.currencyExchange();

        // When
        when(currencyExchangeRepository.findByFromCurrencyAndToCurrency(USD, EUR)).thenReturn(Optional.of(currencyExchange));
        currencyExchangeService.getExchange(currencyExchangeRequestDto);

        // Then
        verify(freeCurrencyService, times(0)).getCurrencyFromApi(any());
    }

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
        when(currencyExchangeRepository.findByFromCurrencyAndToCurrency(USD, EUR)).thenReturn(Optional.of(currencyExchange));
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