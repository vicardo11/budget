package it.sosinski.accountbalance.service.mapper;

import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.repository.entity.Expense;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseMapperTest {

    private final ExpenseMapper expenseMapper = Mappers.getMapper( ExpenseMapper.class);
    private static final BigDecimal VALUE_150 = BigDecimal.valueOf(150);
    private static final LocalDateTime DATE_TIME_2017 = LocalDateTime.of(2017, 1, 1, 10, 10);
    private static final String TITLE_CAR = "Car";

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

    private static Expense expense() {
        return Expense.builder()
                .value(VALUE_150)
                .dateTime(DATE_TIME_2017)
                .title(TITLE_CAR)
                .build();
    }
}