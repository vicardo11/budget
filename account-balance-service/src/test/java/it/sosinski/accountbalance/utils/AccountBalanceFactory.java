package it.sosinski.accountbalance.utils;

import it.sosinski.accountbalance.dto.AccountBalanceResponseDto;

import java.math.BigDecimal;

public class AccountBalanceFactory {

    public static final BigDecimal BALANCE_480 = BigDecimal.valueOf(480);

    public static AccountBalanceResponseDto accountBalanceResponseDto() {
        return AccountBalanceResponseDto.builder()
                .balance(BALANCE_480)
                .build();
    }

}
