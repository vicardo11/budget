package it.sosinski.currencyexchangeservice.service;

import it.sosinski.currencyexchangeservice.dto.FreeCurrencyResponseDto;
import it.sosinski.currencyexchangeservice.repository.CurrencyExchangeRepository;
import it.sosinski.currencyexchangeservice.repository.entity.CurrencyExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyCrudServiceTest {

    private static final String USD = "USD";

    private static final String EUR = "EUR";

    @Mock
    private CurrencyExchangeRepository repository;

    @Mock
    private FreeCurrencyService freeCurrencyService;

    @InjectMocks
    private CurrencyCrudService currencyCrudService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(currencyCrudService, "updateInterval", 10);
    }

    @Test
    void shouldReturnCurrencyExchangeIfPresentAndUpdatedLately() {
        //Given
        final CurrencyExchange currencyExchange = CurrencyExchange.builder()
                .fromCurrency(USD)
                .toCurrency(EUR)
                .lastUpdatedOn(LocalDateTime.now())
                .build();
        when(repository.findByFromCurrencyAndToCurrency(USD, EUR)).thenReturn(Optional.of(currencyExchange));

        //When
        final CurrencyExchange result = currencyCrudService.findCurrencyExchange(USD, EUR);

        //Then
        assertThat(result).isNotNull();
        assertThat(result)
                .extracting(
                        CurrencyExchange::getFromCurrency,
                        CurrencyExchange::getToCurrency)
                .containsExactly(
                        USD,
                        EUR);
    }

    @Test
    void shouldCallExternalApiIfCurrencyExchangeIsNotPresent() {
        //Given
        final CurrencyExchange currencyExchange = CurrencyExchange.builder()
                .fromCurrency(USD)
                .toCurrency(EUR)
                .lastUpdatedOn(LocalDateTime.now())
                .build();
        final FreeCurrencyResponseDto currencyResponseDto = FreeCurrencyResponseDto.builder()
                .rates(Map.of(EUR, BigDecimal.ONE))
                .build();
        when(repository.findByFromCurrencyAndToCurrency(USD, EUR))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(currencyExchange));
        when(freeCurrencyService.getCurrencyFromApi(USD)).thenReturn(currencyResponseDto);

        //When
        final CurrencyExchange result = currencyCrudService.findCurrencyExchange(USD, EUR);

        //Then
        verify(freeCurrencyService).getCurrencyFromApi(USD);
        assertThat(result).isNotNull();
        assertThat(result)
                .extracting(
                        CurrencyExchange::getFromCurrency,
                        CurrencyExchange::getToCurrency)
                .containsExactly(
                        USD,
                        EUR);
    }

    @Test
    void shouldCallExternalApiIfCurrencyExchangeUpdatedLongTimeAgo() {
        //Given
        final CurrencyExchange currencyExchange = CurrencyExchange.builder()
                .fromCurrency(USD)
                .toCurrency(EUR)
                .lastUpdatedOn(LocalDateTime.now().minusSeconds(15))
                .build();
        final FreeCurrencyResponseDto currencyResponseDto = FreeCurrencyResponseDto.builder()
                .rates(Map.of(EUR, BigDecimal.ONE))
                .build();
        when(repository.findByFromCurrencyAndToCurrency(USD, EUR)).thenReturn(Optional.of(currencyExchange));
        when(freeCurrencyService.getCurrencyFromApi(USD)).thenReturn(currencyResponseDto);

        //When
        final CurrencyExchange result = currencyCrudService.findCurrencyExchange(USD, EUR);

        //Then
        verify(freeCurrencyService).getCurrencyFromApi(USD);
        assertThat(result).isNotNull();
        assertThat(result)
                .extracting(
                        CurrencyExchange::getFromCurrency,
                        CurrencyExchange::getToCurrency)
                .containsExactly(
                        USD,
                        EUR);
    }
}