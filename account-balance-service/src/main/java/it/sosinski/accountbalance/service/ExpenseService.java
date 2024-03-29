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

/**
 * Service responsible for operations associate with expenses
 */
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ExpenseMapper expenseMapper;

    /**
     * Returns the list of expenses of the user
     *
     * @param email - email of current user
     * @return - expenses list of the user
     */
    @LogMethodAround
    public ExpenseResponseDtoList getExpensesList(final String email) {
        final List<Expense> expenses = expenseRepository.findAllByEmail(email);
        final List<ExpenseResponseDto> expenseResponseDtos = expenses.stream()
                .map(expenseMapper::toResponseDto)
                .toList();
        return new ExpenseResponseDtoList(expenseResponseDtos);
    }

    /**
     * Creates expense of current user
     *
     * @param email                   - email of current user
     * @param expenseCreateRequestDto - expense to be created
     * @return - created expense
     */
    @LogMethodAround
    public ExpenseResponseDto createExpense(final String email, final ExpenseCreateRequestDto expenseCreateRequestDto) {
        final Expense expense = expenseMapper.toExpense(expenseCreateRequestDto);
        expense.setEmail(email);
        final Expense createdExpense = expenseRepository.save(expense);

        return expenseMapper.toResponseDto(createdExpense);
    }

}
