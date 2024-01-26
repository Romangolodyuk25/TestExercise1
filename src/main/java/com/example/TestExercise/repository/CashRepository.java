package com.example.TestExercise.repository;

import com.example.TestExercise.model.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс, предзназначенный для взаимодействия с базой данных и осуществления CRUD операций
 */
@Repository
public interface CashRepository extends JpaRepository<Cash, Long> {

    Optional<Cash> findByAddress(String address);

    List<Cash> findByCoordinates(String coordinates);
}
