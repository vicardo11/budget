package it.sosinski.accountbalance.utils;

import it.sosinski.accountbalance.dto.ExpenseCreateRequestDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.repository.entity.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ExpenseFactory {

    public static final String EMAIL_TEST_WP = "test@wp.pl";

    public static final String HEADER_EMAIL = "email";

    public static final BigDecimal VALUE_150 = BigDecimal.valueOf(150);

    public static final String TITLE_CAR = "Car";

    public static final LocalDateTime DATE_TIME_2017 = LocalDateTime.of(2017, 1, 1, 10, 10);

    public static final long ID_1 = 1L;

    public static Expense expense() {
        return Expense.builder()
                .value(VALUE_150)
                .dateTime(DATE_TIME_2017)
                .title(TITLE_CAR)
                .build();
    }

    public static Expense expenseWithId1() {
        return Expense.builder()
                .id(ID_1)
                .value(VALUE_150)
                .dateTime(DATE_TIME_2017)
                .title(TITLE_CAR)
                .build();
    }

    public static ExpenseResponseDto expenseResponseDto() {
        return ExpenseResponseDto.builder()
                .value(VALUE_150)
                .dateTime(DATE_TIME_2017)
                .title(TITLE_CAR)
                .build();
    }

    public static ExpenseCreateRequestDto expenseCreateRequestDto() {
        return ExpenseCreateRequestDto
                .builder()
                .value(VALUE_150)
                .title(TITLE_CAR)
                .dateTime(DATE_TIME_2017)
                .build();
    }

    public static ExpenseResponseDtoList expenseResponseDtoListWith1Element() {
        return ExpenseResponseDtoList.builder()
                .expenses(List.of(expenseResponseDto()))
                .build();
    }

}
