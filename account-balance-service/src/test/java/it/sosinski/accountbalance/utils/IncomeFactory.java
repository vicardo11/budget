package it.sosinski.accountbalance.utils;

import it.sosinski.accountbalance.dto.IncomeResponseDto;
import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import it.sosinski.accountbalance.repository.entity.Income;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class IncomeFactory {

    public static final BigDecimal VALUE_150 = BigDecimal.valueOf(150);
    public static final String TITLE_CAR = "Car";
    public static final LocalDateTime DATE_TIME_2017 = LocalDateTime.of(2017, 1, 1, 10, 10);

    public static Income income() {
        return Income.builder()
                .value(VALUE_150)
                .dateTime(DATE_TIME_2017)
                .title(TITLE_CAR)
                .build();
    }

    public static IncomeResponseDto incomeResponseDto() {
        return IncomeResponseDto.builder()
                .value(VALUE_150)
                .dateTime(DATE_TIME_2017)
                .title(TITLE_CAR)
                .build();
    }

    public static IncomeResponseDtoList incomeResponseDtoList() {
        return IncomeResponseDtoList.builder()
                .income(List.of(incomeResponseDto()))
                .build();
    }

}
