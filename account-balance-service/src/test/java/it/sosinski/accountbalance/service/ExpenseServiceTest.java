package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.ExpenseCreateRequestDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.repository.ExpenseRepository;
import it.sosinski.accountbalance.repository.entity.Expense;
import it.sosinski.accountbalance.service.mapper.ExpenseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static it.sosinski.accountbalance.utils.ExpenseFactory.EMAIL_TEST_WP;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expense;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expenseCreateRequestDto;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expenseResponseDto;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expenseWithId1;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

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

    @Test
    void shouldReturnNotNullElement() {
        // Given
        ExpenseCreateRequestDto expenseCreateRequestDto = expenseCreateRequestDto();
        Expense expense = expense();
        Expense createdExpense = expenseWithId1();
        ExpenseResponseDto expenseResponseDto = expenseResponseDto();

        // When
        when(expenseMapper.toExpense(expenseCreateRequestDto)).thenReturn(expense);
        when(expenseRepository.save(expense)).thenReturn(createdExpense);
        when(expenseMapper.toResponseDto(createdExpense)).thenReturn(expenseResponseDto);
        ExpenseResponseDto returnedExpenseResponseDto = expenseService.createExpense(EMAIL_TEST_WP, expenseCreateRequestDto);

        // Then
        assertNotNull(returnedExpenseResponseDto);
    }

    @Test
    void shouldReturnElementWithCorrectValues() {
        // Given
        ExpenseCreateRequestDto expenseCreateRequestDto = expenseCreateRequestDto();
        Expense expense = expense();
        Expense createdExpense = expenseWithId1();
        ExpenseResponseDto expenseResponseDto = expenseResponseDto();

        // When
        when(expenseMapper.toExpense(expenseCreateRequestDto)).thenReturn(expense);
        when(expenseRepository.save(expense)).thenReturn(createdExpense);
        when(expenseMapper.toResponseDto(createdExpense)).thenReturn(expenseResponseDto);
        ExpenseResponseDto returnedExpenseResponseDto = expenseService.createExpense(EMAIL_TEST_WP, expenseCreateRequestDto);

        // Then
        assertAll(
                () -> assertEquals(expenseCreateRequestDto.getValue(), returnedExpenseResponseDto.getValue()),
                () -> assertEquals(expenseCreateRequestDto.getTitle(), returnedExpenseResponseDto.getTitle()),
                () -> assertEquals(expenseCreateRequestDto.getDateTime(), returnedExpenseResponseDto.getDateTime())
        );
    }

}