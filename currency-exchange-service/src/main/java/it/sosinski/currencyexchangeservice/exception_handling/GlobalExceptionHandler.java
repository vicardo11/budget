package it.sosinski.currencyexchangeservice.exception_handling;

import it.sosinski.currencyexchangeservice.exception_handling.exceptions.CurrencyNotSupportedException;
import it.sosinski.currencyexchangeservice.exception_handling.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handler(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        return ErrorResponse.builder()
                .message(fieldErrorsToString(fieldErrors))
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(value = {CurrencyNotSupportedException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handler(CurrencyNotSupportedException exception) {
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
    }

    private String fieldErrorsToString(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }
}