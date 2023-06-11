package it.sosinski.accountbalance.service;

import it.sosinski.accountbalance.dto.AccountBalanceResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.dto.IncomeResponseDto;
import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static it.sosinski.accountbalance.utils.ExpenseFactory.EMAIL_TEST_WP;
import static it.sosinski.accountbalance.utils.ExpenseFactory.expenseResponseDtoListWith1Element;
import static it.sosinski.accountbalance.utils.IncomeFactory.incomeResponseDtoList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountBalanceServiceTest {

    @InjectMocks
    private AccountBalanceService accountBalanceService;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private IncomeService incomeService;

    @Test
    void shouldReturnCorrectBalanceWhenExpensesAndIncome() {
        // Given
        ExpenseResponseDtoList expenseResponseDtoList = expenseResponseDtoListWith1Element();
        IncomeResponseDtoList incomeResponseDtoList = incomeResponseDtoList();

        // When
        when(expenseService.getExpensesList(EMAIL_TEST_WP)).thenReturn(expenseResponseDtoList);
        when(incomeService.getIncomeList(EMAIL_TEST_WP)).thenReturn(incomeResponseDtoList);
        AccountBalanceResponseDto accountBalance = accountBalanceService.getAccountBalance(EMAIL_TEST_WP);

        // Then
        BigDecimal expenseTotal = getExpensesTotal(expenseResponseDtoList);
        BigDecimal incomeTotal = getIncomeTotal(incomeResponseDtoList);
        BigDecimal difference = expenseTotal.subtract(incomeTotal);
        assertEquals(difference, accountBalance.getBalance());
    }

    @Test
    void shouldReturnCorrectBalanceWhenOnlyExpenses() {
        // Given
        ExpenseResponseDtoList expenseResponseDtoList = expenseResponseDtoListWith1Element();

        // When
        when(expenseService.getExpensesList(EMAIL_TEST_WP)).thenReturn(expenseResponseDtoList);
        when(incomeService.getIncomeList(EMAIL_TEST_WP)).thenReturn(new IncomeResponseDtoList());
        AccountBalanceResponseDto accountBalance = accountBalanceService.getAccountBalance(EMAIL_TEST_WP);

        // Then
        BigDecimal expenseTotal = getExpensesTotal(expenseResponseDtoList);
        BigDecimal negatedExpenseTotal = expenseTotal.negate();
        assertEquals(negatedExpenseTotal, accountBalance.getBalance());
    }

    @Test
    void shouldReturnCorrectBalanceWhenOnlyIncome() {
        // Given
        IncomeResponseDtoList incomeResponseDtoList = incomeResponseDtoList();

        // When
        when(expenseService.getExpensesList(EMAIL_TEST_WP)).thenReturn(new ExpenseResponseDtoList());
        when(incomeService.getIncomeList(EMAIL_TEST_WP)).thenReturn(incomeResponseDtoList);
        AccountBalanceResponseDto accountBalance = accountBalanceService.getAccountBalance(EMAIL_TEST_WP);

        // Then
        BigDecimal incomeTotal = getIncomeTotal(incomeResponseDtoList);
        assertEquals(incomeTotal, accountBalance.getBalance());
    }

    private static BigDecimal getExpensesTotal(ExpenseResponseDtoList expenseResponseDtoList) {
        return expenseResponseDtoList.getExpenses()
                .stream()
                .map(ExpenseResponseDto::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getIncomeTotal(IncomeResponseDtoList incomeResponseDtoList) {
        return incomeResponseDtoList.getIncome()
                .stream()
                .map(IncomeResponseDto::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}