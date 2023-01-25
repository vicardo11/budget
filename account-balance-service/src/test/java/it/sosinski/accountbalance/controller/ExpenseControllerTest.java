package it.sosinski.accountbalance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static it.sosinski.accountbalance.configuration.UriConstants.LH_URI_EXPENSES_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ExpenseController.class})
class ExpenseControllerTest {

    private static final String EMAIL_TEST_WP = "test@wp.pl";
    private static final String HEADER_EMAIL = "email";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getListShouldReturnStatusOk() throws Exception {
        // Given

        // When
        mockMvc.perform(get(LH_URI_EXPENSES_LIST)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
    }

    @Test
    void getListShouldReturnOneElementInResponse() throws Exception {
        // Given
        ExpenseResponseDtoList expenseResponseDtoList = expenseResponseDtoListWith1Element();

        // When
        when(expenseService.getExpensesList(EMAIL_TEST_WP)).thenReturn(expenseResponseDtoList);
        MvcResult mvcResult = mockMvc.perform(get(LH_URI_EXPENSES_LIST)
                        .header("email", EMAIL_TEST_WP))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String contentAsString = mvcResult.getResponse()
                .getContentAsString();
        ExpenseResponseDtoList responseExpenseResponseDtoList = objectMapper.readValue(contentAsString, ExpenseResponseDtoList.class);

        assertEquals(1, responseExpenseResponseDtoList.getExpenses().size());
    }

    private static ExpenseResponseDtoList expenseResponseDtoListWith1Element() {
        ExpenseResponseDto expenseResponseDto = ExpenseResponseDto.builder()
                .value(BigDecimal.valueOf(150))
                .build();

        return ExpenseResponseDtoList.builder()
                .expenses(List.of(expenseResponseDto))
                .build();
    }
}