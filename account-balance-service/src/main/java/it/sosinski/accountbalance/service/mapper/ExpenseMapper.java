package it.sosinski.accountbalance.service.mapper;

import it.sosinski.accountbalance.dto.ExpenseCreateRequestDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.repository.entity.Expense;
import org.mapstruct.Mapper;

@Mapper
public interface ExpenseMapper {

    ExpenseResponseDto toResponseDto(final Expense expense);

    Expense toExpense(final ExpenseCreateRequestDto expenseCreateRequestDto);

}
