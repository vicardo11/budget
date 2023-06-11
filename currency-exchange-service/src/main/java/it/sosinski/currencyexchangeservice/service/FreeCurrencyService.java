package it.sosinski.currencyexchangeservice.service;

import it.sosinski.aspectdirectory.logger.LogMethodAround;
import it.sosinski.currencyexchangeservice.dto.FreeCurrencyResponseDto;
import it.sosinski.currencyexchangeservice.exception_handling.exceptions.CurrencyNotSupportedException;
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
    protected FreeCurrencyResponseDto getCurrencyFromApi(final String fromCurrency) {
        final String uri = buildUri(fromCurrency);

        FreeCurrencyResponseDto responseDto = null;
        try {
            responseDto = restTemplate.getForObject(uri, FreeCurrencyResponseDto.class);
        } catch (HttpClientErrorException e) {
            final HttpStatusCode statusCode = e.getStatusCode();
            if (statusCode.value() == 201) {
                LOG.warn("Source currency is not supported.");
                throw new CurrencyNotSupportedException("Source currency is not supported.");
            } else {
                LOG.error("Error connecting to the external API. code - {}", statusCode.value());
                throw e;
            }
        }
        return responseDto;
    }

    private String buildUri(final String fromCurrency) {
        return UriComponentsBuilder.fromHttpUrl(FREECURRENCY_URI + "/latest")
                .queryParam("access_key", API_KEY)
                .queryParam("base", fromCurrency)
                .encode()
                .toUriString();
    }

}
