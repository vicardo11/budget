package it.sosinski.accountbalance.service.mapper;

import it.sosinski.accountbalance.dto.ExpenseCreateRequestDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.repository.entity.Expense;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static it.sosinski.accountbalance.utils.ExpenseFactory.DATE_TIME_2017;
import static it.sosinski.accountbalance.utils.ExpenseFactory.TITLE_CAR;
import static it.sosinski.accountbalance.utils.ExpenseFactory.VALUE_150;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expense;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expenseCreateRequestDto;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseMapperTest {

    private final ExpenseMapper expenseMapper = Mappers.getMapper(ExpenseMapper.class);

    @Test
    void expenseToExpenseResponseDtoShouldMapValues() {
        // Given
        Expense expense = expense();

        // When
        ExpenseResponseDto expenseResponseDto = expenseMapper.toResponseDto(expense);

        // Then
        assertAll(
                () -> assertEquals(VALUE_150, expenseResponseDto.getValue()),
                () -> assertEquals(DATE_TIME_2017, expenseResponseDto.getDateTime()),
                () -> assertEquals(TITLE_CAR, expenseResponseDto.getTitle())
        );
    }

    @Test
    void expenseCreateRequestDtoToExpenseShouldMapValues() {
        // Given
        ExpenseCreateRequestDto expenseCreateRequestDto = expenseCreateRequestDto();

        // When
        Expense expense = expenseMapper.toExpense(expenseCreateRequestDto);

        // Then
        assertAll(
                () -> assertEquals(VALUE_150, expense.getValue()),
                () -> assertEquals(DATE_TIME_2017, expense.getDateTime()),
                () -> assertEquals(TITLE_CAR, expense.getTitle())
        );
    }

}