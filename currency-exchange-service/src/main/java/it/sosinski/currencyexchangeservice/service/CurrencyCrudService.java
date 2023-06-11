package it.sosinski.currencyexchangeservice.service;

import it.sosinski.aspectdirectory.logger.LogMethodAround;
import it.sosinski.currencyexchangeservice.dto.FreeCurrencyResponseDto;
import it.sosinski.currencyexchangeservice.exception_handling.exceptions.CurrencyNotSupportedException;
import it.sosinski.currencyexchangeservice.repository.CurrencyExchangeRepository;
import it.sosinski.currencyexchangeservice.repository.entity.CurrencyExchange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service responsible for currency exchange operations associated with database.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyCrudService {

    private final CurrencyExchangeRepository repository;

    private final FreeCurrencyService freeCurrencyService;

    @Value("${exchange.update.interval}")
    private int updateInterval;

    /**
     * Get currency exchange from the database. If currencies pair doesn't exist in DB or last update was long time ago,
     * there is a call to external API to get the most recent exchange rates. The time (seconds) after which new update
     * is requested is defined in properties.
     * When there is a call to the external API, we get all the currency exchanges for the base currency to limit
     * the number of calls.
     *
     * @param fromCurrency - source currency
     * @param toCurrency   - target currency
     * @return - currency exchange containing the exchange rate
     */
    @LogMethodAround
    public CurrencyExchange findCurrencyExchange(final String fromCurrency, final String toCurrency) {
        Optional<CurrencyExchange> optionalCurrencyExchange = findInRepository(fromCurrency, toCurrency);

        if (optionalCurrencyExchange.isEmpty() || updatedLongTimeAgo(optionalCurrencyExchange.get())) {
            LOG.debug("Getting exchange rates from API for currency - {}", fromCurrency);
            final FreeCurrencyResponseDto ratesFromApi = getRatesFromApi(fromCurrency);
            saveRatesToDatabase(fromCurrency, ratesFromApi);
            optionalCurrencyExchange = findInRepository(fromCurrency, toCurrency);
        }

        if (optionalCurrencyExchange.isPresent()) {
            return optionalCurrencyExchange.get();
        } else {
            LOG.warn("Currency pair is not supported - from {} to {}", fromCurrency, toCurrency);
            throw new CurrencyNotSupportedException("Currency pair is not supported");
        }
    }

    private Optional<CurrencyExchange> findInRepository(final String fromCurrency, final String toCurrency) {
        return repository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
    }

    private boolean updatedLongTimeAgo(final CurrencyExchange currencyExchange) {
        return Duration.between(currencyExchange.getLastUpdatedOn(), LocalDateTime.now()).getSeconds() > updateInterval;
    }

    private FreeCurrencyResponseDto getRatesFromApi(final String fromCurrency) {
        return freeCurrencyService.getCurrencyFromApi(fromCurrency);
    }

    /**
     * Save rates gathered from API to the database.
     * If the specific pair of currencies (fromCurenncy, toCurrency) already exists - update it.
     * If not - save the new record.
     */
    private void saveRatesToDatabase(final String fromCurrency, final FreeCurrencyResponseDto freeCurrencyResponseDto) {
        freeCurrencyResponseDto.getRates()
                .forEach((toCurrency, exchangeRate) -> {
                    final Optional<CurrencyExchange> optionalCurrencyExchange = findInRepository(fromCurrency, toCurrency);
                    CurrencyExchange currencyExchange = null;

                    if (optionalCurrencyExchange.isPresent()) {
                        currencyExchange = optionalCurrencyExchange.get();
                        currencyExchange.setExchangeRate(exchangeRate);
                    } else {
                        currencyExchange = createExchangeRate(fromCurrency, toCurrency, exchangeRate);
                    }
                    saveCurrencyExchange(currencyExchange);
                });
    }

    private CurrencyExchange createExchangeRate(final String fromCurrency, final String toCurrency, final BigDecimal exchangeRate) {
        return CurrencyExchange.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .exchangeRate(exchangeRate)
                .lastUpdatedOn(LocalDateTime.now())
                .build();
    }

    private void saveCurrencyExchange(final CurrencyExchange currencyExchange) {
        repository.save(currencyExchange);
    }

}
