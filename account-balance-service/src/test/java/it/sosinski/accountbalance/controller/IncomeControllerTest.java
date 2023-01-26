package it.sosinski.accountbalance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import it.sosinski.accountbalance.service.IncomeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static it.sosinski.accountbalance.configuration.UriConstants.LH_URI_INCOME_LIST;
import static it.sosinski.accountbalance.utils.ExpenseFactory.EMAIL_TEST_WP;
import static it.sosinski.accountbalance.utils.ExpenseFactory.HEADER_EMAIL;
import static it.sosinski.accountbalance.utils.IncomeFactory.incomeResponseDtoList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {IncomeController.class})
class IncomeControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private IncomeService incomeService;

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
        mockMvc.perform(get(LH_URI_INCOME_LIST)
                        .header(HEADER_EMAIL, EMAIL_TEST_WP))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
    }

    @Test
    void getListShouldReturnOneElementInResponse() throws Exception {
        // Given
        IncomeResponseDtoList incomeResponseDtoList = incomeResponseDtoList();

        // When
        when(incomeService.getIncomeList(EMAIL_TEST_WP)).thenReturn(incomeResponseDtoList);
        MvcResult mvcResult = mockMvc.perform(get(LH_URI_INCOME_LIST)
                        .header("email", EMAIL_TEST_WP))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String contentAsString = mvcResult.getResponse()
                .getContentAsString();
        IncomeResponseDtoList responseIncomeResponseDtoList = objectMapper.readValue(contentAsString, IncomeResponseDtoList.class);

        assertEquals(1, responseIncomeResponseDtoList.getIncome().size());
    }

}