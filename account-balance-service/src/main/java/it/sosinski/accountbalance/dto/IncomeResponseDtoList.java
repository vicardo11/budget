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
public class IncomeResponseDtoList {

    private List<IncomeResponseDto> income;

    @JsonIgnore
    public BigDecimal getTotal() {
        if (CollectionUtils.isEmpty(income)) {
            return BigDecimal.ZERO;
        }
        return income.stream()
                .map(IncomeResponseDto::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
