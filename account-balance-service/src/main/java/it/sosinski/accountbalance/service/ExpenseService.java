package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.ExpenseCreateRequestDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.repository.ExpenseRepository;
import it.sosinski.accountbalance.repository.entity.Expense;
import it.sosinski.accountbalance.service.mapper.ExpenseMapper;
import it.sosinski.aspectdirectory.logger.LogMethodAround;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @LogMethodAround
    public ExpenseResponseDtoList getExpensesList(final String email) {
        List<Expense> expenses = expenseRepository.findAllByEmail(email);
        List<ExpenseResponseDto> expenseResponseDtos = expenses.stream()
                .map(expenseMapper::toResponseDto)
                .toList();
        return new ExpenseResponseDtoList(expenseResponseDtos);
    }

    @LogMethodAround
    public ExpenseResponseDto createExpense(final String email, final ExpenseCreateRequestDto expenseCreateRequestDto) {
        Expense expense = expenseMapper.toExpense(expenseCreateRequestDto);
        expense.setEmail(email);
        Expense createdExpense = expenseRepository.save(expense);

        return expenseMapper.toResponseDto(createdExpense);
    }
}
