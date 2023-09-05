package ru.itis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.Parking;

import java.util.Optional;

public interface ParkingsRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByAddress(String address);
}
