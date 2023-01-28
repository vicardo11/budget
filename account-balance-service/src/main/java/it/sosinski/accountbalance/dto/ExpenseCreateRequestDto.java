package it.sosinski.accountbalance.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ExpenseCreateRequestDto {

    @NotEmpty
    private String title;
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal value;
    private LocalDateTime dateTime = LocalDateTime.now();

}