package com.example.TestExercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Класс - сущность для хранения координат и адресов
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cash")
public class Cash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cash_id")
    private Long id;

    @Column(name = "coordinates", nullable = false, length = 30)
    private String coordinates;

    @Column(name = "address", nullable = false, unique = true, length = 512)
    private String address;
}
