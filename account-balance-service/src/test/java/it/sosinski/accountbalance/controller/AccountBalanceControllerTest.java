package it.sosinski.accountbalance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sosinski.accountbalance.dto.AccountBalanceResponseDto;
import it.sosinski.accountbalance.service.AccountBalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static it.sosinski.accountbalance.configuration.UriConstants.URI_ACCOUNT_BALANCE;
import static it.sosinski.accountbalance.utils.AccountBalanceFactory.accountBalanceResponseDto;
import static it.sosinski.accountbalance.utils.ExpenseFactory.EMAIL_TEST_WP;
import static it.sosinski.accountbalance.utils.ExpenseFactory.HEADER_EMAIL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AccountBalanceController.class})
public class AccountBalanceControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private AccountBalanceService accountBalanceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnStatusIsOk() throws Exception {
        // Given

        // When
        mockMvc.perform(get(URI_ACCOUNT_BALANCE)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
    }

    @Test
    void shouldReturnNotNullResponse() throws Exception {
        // Given
        AccountBalanceResponseDto accountBalanceResponseDto = accountBalanceResponseDto();

        // When
        when(accountBalanceService.getAccountBalance(EMAIL_TEST_WP)).thenReturn(accountBalanceResponseDto);
        MvcResult mvcResult = mockMvc.perform(get(URI_ACCOUNT_BALANCE)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        AccountBalanceResponseDto responseAccountBalanceResponseDto = objectMapper.readValue(contentAsString, AccountBalanceResponseDto.class);

        assertNotNull(responseAccountBalanceResponseDto);
    }

    @Test
    void shouldReturnNotNullBalance() throws Exception {
        // Given
        AccountBalanceResponseDto accountBalanceResponseDto = accountBalanceResponseDto();

        // When
        when(accountBalanceService.getAccountBalance(EMAIL_TEST_WP)).thenReturn(accountBalanceResponseDto);
        MvcResult mvcResult = mockMvc.perform(get(URI_ACCOUNT_BALANCE)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        AccountBalanceResponseDto responseAccountBalanceResponseDto = objectMapper.readValue(contentAsString, AccountBalanceResponseDto.class);

        assertNotNull(responseAccountBalanceResponseDto.getBalance());
    }

    @Test
    void shouldReturnBadRequestWhenNoEmailHeaderProvided() throws Exception {
        // Given

        // When
        mockMvc.perform(get(URI_ACCOUNT_BALANCE))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // Then
    }

}
