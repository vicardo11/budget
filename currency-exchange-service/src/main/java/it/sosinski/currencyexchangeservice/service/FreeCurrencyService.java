package it.sosinski.currencyexchangeservice.service;

import it.sosinski.aspectdirectory.logger.LogMethodAround;
import it.sosinski.currencyexchangeservice.dto.FreeCurrencyDto;
import it.sosinski.currencyexchangeservice.dto.FreeCurrencyResponseDto;
import it.sosinski.currencyexchangeservice.exception_handling.excpetions.CurrencyNotSupportedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
@RequiredArgsConstructor
public class FreeCurrencyService {

    private final RestTemplate restTemplate;
    @Value("${freecurrency.apikey}")
    private String API_KEY;
    @Value("${freecurrency.uri}")
    private String FREECURRENCY_URI;

    @LogMethodAround
    protected FreeCurrencyResponseDto getCurrencyFromApi(FreeCurrencyDto freeCurrencyDto) {
        String fromCurrency = freeCurrencyDto.getFromCurrency();
        String toCurrency = freeCurrencyDto.getToCurrency();

        String uri = buildUri(fromCurrency, toCurrency);

        FreeCurrencyResponseDto responseDto = null;
        try {
            responseDto = restTemplate.getForObject(uri, FreeCurrencyResponseDto.class);
        } catch (HttpClientErrorException e) {
            HttpStatusCode statusCode = e.getStatusCode();
            if (statusCode.value() == 422 && e.getMessage().contains("base_currency")) {
                log.warn("Source currency is not supported.");
                throw new CurrencyNotSupportedException("Source currency is not supported.");
            }
            if (statusCode.value() == 422) {
                log.warn("Target currency is not supported.");
                throw new CurrencyNotSupportedException("Target currency is not supported.");
            }
        }
        return responseDto;
    }

    private String buildUri(String fromCurrency, String toCurrency) {
        return UriComponentsBuilder.fromHttpUrl(FREECURRENCY_URI)
                .queryParam("apikey", API_KEY)
                .queryParam("base_currency", fromCurrency)
                .queryParam("currencies", toCurrency)
                .encode()
                .toUriString();
    }
}
