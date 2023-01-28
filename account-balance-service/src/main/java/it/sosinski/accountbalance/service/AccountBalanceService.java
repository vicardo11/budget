package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.AccountBalanceResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import it.sosinski.aspectdirectory.logger.LogMethodAround;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountBalanceService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    @LogMethodAround
    public AccountBalanceResponseDto getAccountBalance(String email) {
        ExpenseResponseDtoList expensesList = getExpensesList(email);
        IncomeResponseDtoList incomeList = getIncomeList(email);
        BigDecimal expensesTotal = expensesList.getTotal();
        BigDecimal incomeTotal = incomeList.getTotal();
        BigDecimal difference = incomeTotal.subtract(expensesTotal);
        return new AccountBalanceResponseDto(difference);
    }

    private ExpenseResponseDtoList getExpensesList(String email) {
        return expenseService.getExpensesList(email);
    }

    private IncomeResponseDtoList getIncomeList(String email) {
        return incomeService.getIncomeList(email);
    }
}
