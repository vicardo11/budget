package it.sosinski.currencyexchangeservice.exception_handling.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CurrencyNotSupportedException extends RuntimeException {

    private String message;
}
