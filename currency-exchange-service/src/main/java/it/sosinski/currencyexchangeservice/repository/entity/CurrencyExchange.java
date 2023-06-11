package it.sosinski.currencyexchangeservice.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "currency_exchange",
        uniqueConstraints = @UniqueConstraint(columnNames = {"from_currency", "to_currency"}))
public class CurrencyExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_currency")
    private String fromCurrency;

    @Column(name = "to_currency")
    private String toCurrency;

    private BigDecimal exchangeRate;

    private LocalDateTime lastUpdatedOn;

}
