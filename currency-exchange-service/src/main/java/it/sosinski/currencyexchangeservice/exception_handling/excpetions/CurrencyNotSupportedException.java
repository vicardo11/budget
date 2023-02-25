package it.sosinski.currencyexchangeservice.exception_handling.excpetions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CurrencyNotSupportedException extends RuntimeException {

    private String message;
}
