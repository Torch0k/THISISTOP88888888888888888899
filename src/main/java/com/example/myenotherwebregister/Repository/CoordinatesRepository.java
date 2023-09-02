package com.example.myenotherwebregister.Repository;

import com.example.myenotherwebregister.model.polzovatelCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepository extends JpaRepository<polzovatelCoordinates, Long> {
    // Здесь можно добавить дополнительные методы, если нужно
}