package it.sosinski.currencyexchangeservice.service;

import it.sosinski.aspectdirectory.logger.LogMethodAround;
import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeRequestDto;
import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeResponseDto;
import it.sosinski.currencyexchangeservice.dto.FreeCurrencyDto;
import it.sosinski.currencyexchangeservice.dto.FreeCurrencyResponseDto;
import it.sosinski.currencyexchangeservice.repository.CurrencyExchangeRepository;
import it.sosinski.currencyexchangeservice.repository.entity.CurrencyExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {

    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final FreeCurrencyService freeCurrencyService;

    @LogMethodAround
    public CurrencyExchangeResponseDto getExchange(CurrencyExchangeRequestDto currencyExchangeRequestDto) {
        String fromCurrency = currencyExchangeRequestDto.getFromCurrency();
        String toCurrency = currencyExchangeRequestDto.getToCurrency();
        BigDecimal fromValue = currencyExchangeRequestDto.getFromValue();

        Optional<CurrencyExchange> currencyExchange = currencyExchangeRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        if (currencyExchange.isPresent()) {
            BigDecimal exchangeRate = currencyExchange.get()
                    .getExchangeRate();

            BigDecimal toValue = calculateValue(fromValue, exchangeRate);
            return CurrencyExchangeResponseDto.builder()
                    .fromCurrency(fromCurrency)
                    .toCurrency(toCurrency)
                    .fromValue(fromValue)
                    .toValue(toValue)
                    .build();
        } else {
            BigDecimal exchangeRate = getExchangeRateFromApi(fromCurrency, toCurrency);
            saveCurrencyExchangeToDatabase(fromCurrency, toCurrency, exchangeRate);
            var toValue = calculateValue(fromValue, exchangeRate);

            return new CurrencyExchangeResponseDto(fromCurrency, toCurrency, fromValue, toValue);
        }
    }

    private BigDecimal calculateValue(BigDecimal fromValue, BigDecimal exchangeRate) {
        return fromValue.multiply(exchangeRate);
    }

    private BigDecimal getExchangeRateFromApi(String fromCurrency, String toCurrency) {
        FreeCurrencyResponseDto currencyFromApi = freeCurrencyService.getCurrencyFromApi(new FreeCurrencyDto(fromCurrency, toCurrency));
        return currencyFromApi.getData()
                .get(toCurrency);
    }

    private void saveCurrencyExchangeToDatabase(String fromCurrency, String toCurrency, BigDecimal exchangeRate) {
        CurrencyExchange newCurrencyExchange = CurrencyExchange.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .exchangeRate(exchangeRate)
                .build();
        currencyExchangeRepository.save(newCurrencyExchange);
    }
}
