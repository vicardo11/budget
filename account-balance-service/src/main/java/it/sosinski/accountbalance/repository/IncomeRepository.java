package it.sosinski.accountbalance.repository;

import it.sosinski.accountbalance.repository.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByEmail(String email);

}
