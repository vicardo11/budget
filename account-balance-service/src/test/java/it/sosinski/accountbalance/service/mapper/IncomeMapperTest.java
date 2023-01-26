package it.sosinski.accountbalance.service.mapper;

import it.sosinski.accountbalance.dto.IncomeResponseDto;
import it.sosinski.accountbalance.repository.entity.Income;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static it.sosinski.accountbalance.utils.ExpenseFactory.*;
import static it.sosinski.accountbalance.utils.IncomeFactory.income;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IncomeMapperTest {

    private final IncomeMapper incomeMapper = Mappers.getMapper(IncomeMapper.class);

    @Test
    void incomeToIncomeResponseDtoShouldMapValues() {
        // Given
        Income income = income();

        // When
        IncomeResponseDto incomeResponseDto = incomeMapper.toResponseDto(income);

        // Then
        assertAll(
                () -> assertEquals(VALUE_150, incomeResponseDto.getValue()),
                () -> assertEquals(DATE_TIME_2017, incomeResponseDto.getDateTime()),
                () -> assertEquals(TITLE_CAR, incomeResponseDto.getTitle())
        );
    }

}