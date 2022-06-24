package ru.itis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.CarEntry;

import java.util.Optional;

public interface CarEntryRepository extends JpaRepository<CarEntry, Long> {
    Optional<CarEntry> findByCarNumber(String carNumber);
}
