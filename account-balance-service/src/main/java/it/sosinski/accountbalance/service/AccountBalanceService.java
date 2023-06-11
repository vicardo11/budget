package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.AccountBalanceResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import it.sosinski.aspectdirectory.logger.LogMethodAround;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service calculates and provides account balance of the user
 */
@Service
@RequiredArgsConstructor
public class AccountBalanceService {

    private final ExpenseService expenseService;

    private final IncomeService incomeService;

    /**
     * Get account balance of the current user
     *
     * @param email - email of current user
     * @return - account balance of the user
     */
    @LogMethodAround
    public AccountBalanceResponseDto getAccountBalance(final String email) {
        final ExpenseResponseDtoList expensesList = getExpensesList(email);
        final IncomeResponseDtoList incomeList = getIncomeList(email);
        final BigDecimal expensesTotal = expensesList.getTotal();
        final BigDecimal incomeTotal = incomeList.getTotal();
        final BigDecimal difference = incomeTotal.subtract(expensesTotal);
        return new AccountBalanceResponseDto(difference);
    }

    private ExpenseResponseDtoList getExpensesList(final String email) {
        return expenseService.getExpensesList(email);
    }

    private IncomeResponseDtoList getIncomeList(final String email) {
        return incomeService.getIncomeList(email);
    }

}
