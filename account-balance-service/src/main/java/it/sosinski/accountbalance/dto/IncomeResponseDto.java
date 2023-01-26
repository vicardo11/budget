package it.sosinski.accountbalance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeResponseDto {

    private String title;
    private BigDecimal value;
    private LocalDateTime dateTime;

}
