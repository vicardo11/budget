package it.sosinski.accountbalance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.sosinski.accountbalance.dto.ExpenseCreateRequestDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.service.ExpenseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static it.sosinski.accountbalance.configuration.UriConstants.URI_EXPENSES_CREATE;
import static it.sosinski.accountbalance.configuration.UriConstants.URI_EXPENSES_LIST;
import static it.sosinski.accountbalance.utils.ExpenseFactory.DATE_TIME_2017;
import static it.sosinski.accountbalance.utils.ExpenseFactory.EMAIL_TEST_WP;
import static it.sosinski.accountbalance.utils.ExpenseFactory.HEADER_EMAIL;
import static it.sosinski.accountbalance.utils.ExpenseFactory.TITLE_CAR;
import static it.sosinski.accountbalance.utils.ExpenseFactory.VALUE_150;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expenseCreateRequestDto;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expenseResponseDto;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expenseResponseDtoListWith1Element;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ExpenseController.class})
class ExpenseControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getListShouldReturnStatusOk() throws Exception {
        // Given

        // When
        mockMvc.perform(get(URI_EXPENSES_LIST)
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
        MvcResult mvcResult = mockMvc.perform(get(URI_EXPENSES_LIST)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String contentAsString = mvcResult.getResponse()
                .getContentAsString();
        ExpenseResponseDtoList responseExpenseResponseDtoList = objectMapper.readValue(contentAsString, ExpenseResponseDtoList.class);

        assertEquals(1, responseExpenseResponseDtoList.getExpenses().size());
    }

    @Test
    void createExpenseShouldReturnStatusCreated() throws Exception {
        // Given
        ExpenseCreateRequestDto expenseCreateRequestDto = expenseCreateRequestDto();
        String valueAsString = objectMapper.writeValueAsString(expenseCreateRequestDto);

        // When
        mockMvc.perform(post(URI_EXPENSES_CREATE)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andDo(print())
                .andExpect(status().isCreated());

        // Then
    }

    @Test
    void createExpenseShouldReturnBadRequestWhenNoTitleProvided() throws Exception {
        // Given
        ExpenseCreateRequestDto expenseCreateRequestDto = ExpenseCreateRequestDto.builder()
                .dateTime(DATE_TIME_2017)
                .value(VALUE_150)
                .build();
        String valueAsString = objectMapper.writeValueAsString(expenseCreateRequestDto);

        // When
        mockMvc.perform(post(URI_EXPENSES_CREATE)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // Then
    }

    @Test
    void createExpenseShouldReturnBadRequestWhenNoValueProvided() throws Exception {
        // Given
        ExpenseCreateRequestDto expenseCreateRequestDto = ExpenseCreateRequestDto.builder()
                .dateTime(DATE_TIME_2017)
                .title(TITLE_CAR)
                .build();
        String valueAsString = objectMapper.writeValueAsString(expenseCreateRequestDto);

        // When
        mockMvc.perform(post(URI_EXPENSES_CREATE)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // Then
    }

    @Test
    void createExpenseShouldReturnElementWithCorrectValues() throws Exception {
        // Given
        ExpenseCreateRequestDto expenseCreateRequestDto = expenseCreateRequestDto();
        ExpenseResponseDto expenseResponseDto = expenseResponseDto();

        String valueAsString = objectMapper.writeValueAsString(expenseCreateRequestDto);

        // When
        when(expenseService.createExpense(EMAIL_TEST_WP, expenseCreateRequestDto)).thenReturn(expenseResponseDto);
        MvcResult mvcResult = mockMvc.perform(post(URI_EXPENSES_CREATE)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        String contentAsString = mvcResult.getResponse()
                .getContentAsString();
        ExpenseResponseDto expenseResponseDtoResult = objectMapper.readValue(contentAsString, ExpenseResponseDto.class);

        assertAll(
                () -> assertEquals(VALUE_150, expenseResponseDtoResult.getValue()),
                () -> assertEquals(TITLE_CAR, expenseResponseDtoResult.getTitle())
        );
    }


}