package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.repository.ExpenseRepository;
import it.sosinski.accountbalance.repository.entity.Expense;
import it.sosinski.accountbalance.service.mapper.ExpenseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    private static final String EMAIL_TEST_WP = "test@wp.pl";
    private static final long ID_1 = 1L;
    private static final BigDecimal VALUE_150 = BigDecimal.valueOf(150);

    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private ExpenseMapper expenseMapper;
    
    @Test
    void shouldReturnOneElement() {
        // Given
        Expense expense = expense();
        List<Expense> expenseList = List.of(expense);

        // When
        when(expenseRepository.findAllByEmail(EMAIL_TEST_WP)).thenReturn(expenseList);
        ExpenseResponseDtoList expensesList = expenseService.getExpensesList(EMAIL_TEST_WP);

        // Then
        assertEquals(1, expensesList.getExpenses().size());
    }

    @Test
    void shouldReturnNoElements() {
        // Given

        // When
        when(expenseRepository.findAllByEmail(EMAIL_TEST_WP)).thenReturn(Collections.emptyList());
        ExpenseResponseDtoList expensesList = expenseService.getExpensesList(EMAIL_TEST_WP);

        // Then
        assertEquals(0, expensesList.getExpenses().size());
    }

    private static Expense expense() {
        return Expense.builder()
                .email(EMAIL_TEST_WP)
                .id(ID_1)
                .value(VALUE_150)
                .build();
    }
}