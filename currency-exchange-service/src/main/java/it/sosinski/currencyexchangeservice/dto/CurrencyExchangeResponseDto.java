package it.sosinski.currencyexchangeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeResponseDto {

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal fromValue;

    private BigDecimal toValue;

}
