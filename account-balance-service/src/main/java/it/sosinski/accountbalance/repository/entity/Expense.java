package it.sosinski.accountbalance.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Expense extends Payment {

    @Builder
    public Expense(Long id, String title, String email, BigDecimal value, LocalDateTime dateTime) {
        super(id, title, email, value, dateTime);
    }
}
