package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.IncomeCreateRequestDto;
import it.sosinski.accountbalance.dto.IncomeResponseDto;
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
import static it.sosinski.accountbalance.utils.IncomeFactory.incomeCreateRequestDto;
import static it.sosinski.accountbalance.utils.IncomeFactory.incomeResponseDto;
import static it.sosinski.accountbalance.utils.IncomeFactory.incomeWithId1;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    void createdIncomeShouldNotBeNull() {
        // Given
        IncomeCreateRequestDto incomeCreateRequestDto = incomeCreateRequestDto();
        Income income = income();
        Income createdIncome = incomeWithId1();
        IncomeResponseDto incomeResponseDto = incomeResponseDto();

        // When
        when(incomeMapper.toIncome(incomeCreateRequestDto)).thenReturn(income);
        when(incomeRepository.save(income)).thenReturn(createdIncome);
        when(incomeMapper.toResponseDto(createdIncome)).thenReturn(incomeResponseDto);
        IncomeResponseDto resultIncomeResponseDto = incomeService.createIncome(EMAIL_TEST_WP, incomeCreateRequestDto);

        // Then
        assertNotNull(resultIncomeResponseDto);
    }

    @Test
    void createdIncomeShouldHaveTheSameValuesAsProvidedInRequest() {
        // Given
        IncomeCreateRequestDto incomeCreateRequestDto = incomeCreateRequestDto();
        Income income = income();
        Income createdIncome = incomeWithId1();
        IncomeResponseDto incomeResponseDto = incomeResponseDto();

        // When
        when(incomeMapper.toIncome(incomeCreateRequestDto)).thenReturn(income);
        when(incomeRepository.save(income)).thenReturn(createdIncome);
        when(incomeMapper.toResponseDto(createdIncome)).thenReturn(incomeResponseDto);
        IncomeResponseDto resultIncomeResponseDto = incomeService.createIncome(EMAIL_TEST_WP, incomeCreateRequestDto);

        // Then
        assertAll(
                () -> assertEquals(incomeCreateRequestDto.getValue(), resultIncomeResponseDto.getValue()),
                () -> assertEquals(incomeCreateRequestDto.getTitle(), resultIncomeResponseDto.getTitle()),
                () -> assertEquals(incomeCreateRequestDto.getDateTime(), resultIncomeResponseDto.getDateTime())
        );
    }

}