package it.sosinski.currencyexchangeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sosinski.currencyexchangeservice.dto.CurrencyExchangeResponseDto;
import it.sosinski.currencyexchangeservice.exception_handling.exceptions.CurrencyNotSupportedException;
import it.sosinski.currencyexchangeservice.factory.CurrencyExchangeFactory;
import it.sosinski.currencyexchangeservice.service.CurrencyExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static it.sosinski.currencyexchangeservice.configuration.UriConstants.URI_CURRENCY_EXCHANGE;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CurrencyExchangeController.class})
class CurrencyExchangeControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CurrencyExchangeService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnStatusOk() throws Exception {
        // Given
        var exchangeRequestDto = CurrencyExchangeFactory.currencyExchangeRequestDto();
        var valueAsString = objectMapper.writeValueAsString(exchangeRequestDto);

        // When
        mockMvc.perform(get(URI_CURRENCY_EXCHANGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
    }

    @Test
    void shouldReturnNonNullResponse() throws Exception {
        // Given
        var exchangeRequestDto = CurrencyExchangeFactory.currencyExchangeRequestDto();
        var exchangeResponseDto = CurrencyExchangeFactory.currencyExchangeResponseDto();
        var valueAsString = objectMapper.writeValueAsString(exchangeRequestDto);

        // When
        when(service.getExchange(exchangeRequestDto)).thenReturn(exchangeResponseDto);
        var result = mockMvc.perform(get(URI_CURRENCY_EXCHANGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        var contentAsString = result.getResponse().getContentAsString();
        var currencyExchangeResponseDto = objectMapper.readValue(contentAsString, CurrencyExchangeResponseDto.class);
        assertNotNull(currencyExchangeResponseDto);
    }

    @Test
    void shouldReturnStatusBadRequestWhenCurrencyNotSupported() throws Exception {
        // Given
        var exchangeRequestDto = CurrencyExchangeFactory.currencyExchangeRequestDto();
        var valueAsString = objectMapper.writeValueAsString(exchangeRequestDto);

        // When
        when(service.getExchange(exchangeRequestDto)).thenThrow(CurrencyNotSupportedException.class);
        mockMvc.perform(get(URI_CURRENCY_EXCHANGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // Then
    }


}