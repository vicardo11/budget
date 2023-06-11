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

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncomeResponseDtoListTest {

    @Test
    void shouldReturnZeroWhenNoIncome() {
        // Given
        IncomeResponseDtoList incomeResponseDtoList = IncomeResponseDtoList.builder()
                .income(Collections.emptyList())
                .build();

        // When
        BigDecimal total = incomeResponseDtoList.getTotal();

        // Then
        assertEquals(BigDecimal.ZERO, total);
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 13.45, 1.12, 2, 44})
    void shouldReturnCorrectValueWhenOneIncome(double value) {
        // Given
        IncomeResponseDto incomeResponseDto = IncomeResponseDto.builder()
                .value(BigDecimal.valueOf(value))
                .build();

        IncomeResponseDtoList incomeResponseDtoList = IncomeResponseDtoList.builder()
                .income(List.of(incomeResponseDto))
                .build();

        // When
        BigDecimal total = incomeResponseDtoList.getTotal();

        // Then
        BigDecimal expected = BigDecimal.valueOf(value);
        assertEquals(expected, total);
    }

    @ParameterizedTest
    @MethodSource("twoIncomeArguments")
    void shouldReturnCorrectValueWhenTwoIncome(double value1, double value2, double expected) {
        // Given
        IncomeResponseDto incomeResponseDto1 = IncomeResponseDto.builder()
                .value(BigDecimal.valueOf(value1))
                .build();

        IncomeResponseDto incomeResponseDto2 = IncomeResponseDto.builder()
                .value(BigDecimal.valueOf(value2))
                .build();

        IncomeResponseDtoList incomeResponseDtoList = IncomeResponseDtoList.builder()
                .income(List.of(incomeResponseDto1, incomeResponseDto2))
                .build();

        // When
        BigDecimal total = incomeResponseDtoList.getTotal();

        // Then
        BigDecimal expectedBigDecimal = BigDecimal.valueOf(expected);
        assertEquals(expectedBigDecimal, total);
    }

    private static Stream<Arguments> twoIncomeArguments() {
        return Stream.of(
                Arguments.of(2.44, 1.14, 3.58),
                Arguments.of(5.17, 21.48, 26.65),
                Arguments.of(47.48, 47.14, 94.62)
        );
    }

}