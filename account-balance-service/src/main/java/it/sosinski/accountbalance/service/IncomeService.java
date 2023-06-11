package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.IncomeCreateRequestDto;
import it.sosinski.accountbalance.dto.IncomeResponseDto;
import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import it.sosinski.accountbalance.repository.IncomeRepository;
import it.sosinski.accountbalance.repository.entity.Income;
import it.sosinski.accountbalance.service.mapper.IncomeMapper;
import it.sosinski.aspectdirectory.logger.LogMethodAround;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;

    private final IncomeMapper incomeMapper;

    @LogMethodAround
    public IncomeResponseDtoList getIncomeList(final String email) {
        final List<Income> incomeList = incomeRepository.findAllByEmail(email);
        final List<IncomeResponseDto> incomeResponseDtos = incomeList.stream()
                .map(incomeMapper::toResponseDto)
                .collect(Collectors.toList());
        return new IncomeResponseDtoList(incomeResponseDtos);
    }

    @LogMethodAround
    public IncomeResponseDto createIncome(final String email, final IncomeCreateRequestDto incomeCreateRequestDto) {
        final Income income = incomeMapper.toIncome(incomeCreateRequestDto);
        income.setEmail(email);
        final Income createdIncome = incomeRepository.save(income);
        return incomeMapper.toResponseDto(createdIncome);
    }

}
