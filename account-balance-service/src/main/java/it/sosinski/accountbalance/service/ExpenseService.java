package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.repository.ExpenseRepository;
import it.sosinski.accountbalance.repository.entity.Expense;
import it.sosinski.accountbalance.service.mapper.ExpenseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseResponseDtoList getExpensesList(String email) {
        List<Expense> expenses = expenseRepository.findAllByEmail(email);
        List<ExpenseResponseDto> expenseResponseDtos = expenses.stream()
                .map(expenseMapper::toResponseDto)
                .toList();
        return new ExpenseResponseDtoList(expenseResponseDtos);
    }
}
