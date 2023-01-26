package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import it.sosinski.accountbalance.repository.IncomeRepository;
import it.sosinski.accountbalance.repository.entity.Income;
import it.sosinski.accountbalance.service.mapper.IncomeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static it.sosinski.accountbalance.utils.ExpenseFactory.EMAIL_TEST_WP;
import static it.sosinski.accountbalance.utils.IncomeFactory.income;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTest {

    @InjectMocks
    private IncomeService incomeService;

    @Mock
    private IncomeRepository incomeRepository;
    @Mock
    private IncomeMapper incomeMapper;

    @Test
    void shouldReturnOneElement() {
        // Given
        Income income = income();
        List<Income> incomeList = List.of(income);

        // When
        when(incomeRepository.findAllByEmail(EMAIL_TEST_WP)).thenReturn(incomeList);
        IncomeResponseDtoList incomeListResult = incomeService.getIncomeList(EMAIL_TEST_WP);

        // Then
        assertEquals(1, incomeListResult.getIncome().size());
    }

    @Test
    void shouldReturnNoElements() {
        // Given

        // When
        when(incomeRepository.findAllByEmail(EMAIL_TEST_WP)).thenReturn(Collections.emptyList());
        IncomeResponseDtoList incomeList = incomeService.getIncomeList(EMAIL_TEST_WP);

        // Then
        assertEquals(0, incomeList.getIncome().size());
    }

}