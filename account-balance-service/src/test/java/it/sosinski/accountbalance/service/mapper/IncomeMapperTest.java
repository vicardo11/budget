package it.sosinski.accountbalance.service.mapper;

import it.sosinski.accountbalance.dto.IncomeCreateRequestDto;
import it.sosinski.accountbalance.dto.IncomeResponseDto;
import it.sosinski.accountbalance.repository.entity.Income;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static it.sosinski.accountbalance.utils.ExpenseFactory.DATE_TIME_2017;
import static it.sosinski.accountbalance.utils.ExpenseFactory.TITLE_CAR;
import static it.sosinski.accountbalance.utils.ExpenseFactory.VALUE_150;
import static it.sosinski.accountbalance.utils.IncomeFactory.income;
import static it.sosinski.accountbalance.utils.IncomeFactory.incomeCreateRequestDto;
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

    @Test
    void incomeCreateRequestDtoToIncomeShouldMapValues() {
        // Given
        IncomeCreateRequestDto incomeCreateRequestDto = incomeCreateRequestDto();

        // When
        Income income = incomeMapper.toIncome(incomeCreateRequestDto);

        // Then
        assertAll(
                () -> assertEquals(VALUE_150, income.getValue()),
                () -> assertEquals(DATE_TIME_2017, income.getDateTime()),
                () -> assertEquals(TITLE_CAR, income.getTitle())
        );
    }

}