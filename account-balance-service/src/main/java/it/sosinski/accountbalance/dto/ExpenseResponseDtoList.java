package it.sosinski.accountbalance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseDtoList {

    private List<ExpenseResponseDto> expenses;

    @JsonIgnore
    public BigDecimal getTotal() {
        if (CollectionUtils.isEmpty(expenses)) {
            return BigDecimal.ZERO;
        }
        return expenses.stream()
                .map(ExpenseResponseDto::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
