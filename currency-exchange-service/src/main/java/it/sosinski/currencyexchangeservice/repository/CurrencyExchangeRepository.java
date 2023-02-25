package it.sosinski.currencyexchangeservice.repository;

import it.sosinski.currencyexchangeservice.repository.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

    Optional<CurrencyExchange> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);

}
