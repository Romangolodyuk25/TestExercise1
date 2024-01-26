package com.example.TestExercise.repository;

import com.example.TestExercise.model.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashRepository extends JpaRepository<Cash, Long> {
    List<Cash> findByAddress(String address);

    List<Cash> findByCoordinates(String coordinates);
}
