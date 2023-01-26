package it.sosinski.accountbalance.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Income extends Payment {

    @Builder
    public Income(Long id, String title, String email, BigDecimal value, LocalDateTime dateTime) {
        super(id, title, email, value, dateTime);
    }
}
