package it.sosinski.currencyexchangeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreeCurrencyDto {

    private String fromCurrency;
    private String toCurrency;

}
