package it.sosinski.accountbalance.service.mapper;

import it.sosinski.accountbalance.dto.IncomeResponseDto;
import it.sosinski.accountbalance.repository.entity.Income;
import org.mapstruct.Mapper;

@Mapper
public interface IncomeMapper {

    IncomeResponseDto toResponseDto(Income income);

}
