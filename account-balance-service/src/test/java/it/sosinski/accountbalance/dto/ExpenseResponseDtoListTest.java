package it.sosinski.accountbalance.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseResponseDtoListTest {
    
    @Test
    void shouldReturnZeroWhenNoExpense() {
        // Given
        ExpenseResponseDtoList expenseResponseDtoList = ExpenseResponseDtoList.builder()
                .expenses(Collections.emptyList())
                .build();

        // When
        BigDecimal total = expenseResponseDtoList.getTotal();

        // Then
        assertEquals(BigDecimal.ZERO, total);
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 13.45, 1.12, 2,44})
    void shouldReturnCorrectValueWhenOneExpense(double value) {
        // Given
        ExpenseResponseDto expenseResponseDto = ExpenseResponseDto.builder()
                .value(BigDecimal.valueOf(value))
                .build();

        ExpenseResponseDtoList expenseResponseDtoList = ExpenseResponseDtoList.builder()
                .expenses(List.of(expenseResponseDto))
                .build();

        // When
        BigDecimal total = expenseResponseDtoList.getTotal();

        // Then
        BigDecimal expected = BigDecimal.valueOf(value);
        assertEquals(expected, total);
    }

    @ParameterizedTest
    @MethodSource("twoExpenseArguments")
    void shouldReturnCorrectValueWhenTwoExpense(double value1, double value2, double expected) {
        // Given
        ExpenseResponseDto expenseResponseDto1 = ExpenseResponseDto.builder()
                .value(BigDecimal.valueOf(value1))
                .build();

        ExpenseResponseDto expenseResponseDto2 = ExpenseResponseDto.builder()
                .value(BigDecimal.valueOf(value2))
                .build();

        ExpenseResponseDtoList expenseResponseDtoList = ExpenseResponseDtoList.builder()
                .expenses(List.of(expenseResponseDto1, expenseResponseDto2))
                .build();

        // When
        BigDecimal total = expenseResponseDtoList.getTotal();

        // Then
        BigDecimal expectedBigDecimal = BigDecimal.valueOf(expected);
        assertEquals(expectedBigDecimal, total);
    }

    private static Stream<Arguments> twoExpenseArguments() {
        return Stream.of(
                Arguments.of(2.44, 1.14, 3.58),
                Arguments.of(5.17, 21.48, 26.65),
                Arguments.of(47.48, 47.14, 94.62)
        );
    }
}