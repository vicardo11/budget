package it.sosinski.currencyexchangeservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeRequestDto {

    @NotEmpty
    private String fromCurrency;

    @NotEmpty
    private String toCurrency;

    @DecimalMin("0.01")
    private BigDecimal fromValue;

}
