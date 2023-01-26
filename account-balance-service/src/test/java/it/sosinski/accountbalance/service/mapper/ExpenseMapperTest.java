package it.sosinski.accountbalance.service.mapper;

import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.repository.entity.Expense;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseMapperTest {

    private static final BigDecimal VALUE_150 = BigDecimal.valueOf(150);
    private static final LocalDateTime DATE_TIME_2017 = LocalDateTime.of(2017, 1, 1, 10, 10);
    private final ExpenseMapper expenseMapper = Mappers.getMapper( ExpenseMapper.class);

    @Test
    void shouldMapValue() {
        // Given
        Expense expense = Expense.builder()
                .value(VALUE_150)
                .build();

        // When
        ExpenseResponseDto expenseResponseDto = expenseMapper.toResponseDto(expense);

        // Then
        assertEquals(VALUE_150, expenseResponseDto.getValue());
    }

    @Test
    void shouldMapDateTime() {
        // Given
        Expense expense = Expense.builder()
                .dateTime(DATE_TIME_2017)
                .build();

        // When
        ExpenseResponseDto expenseResponseDto = expenseMapper.toResponseDto(expense);

        // Then
        assertEquals(DATE_TIME_2017, expenseResponseDto.getDateTime());
    }


}