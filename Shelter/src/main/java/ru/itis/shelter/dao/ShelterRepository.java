package ru.itis.shelter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.shelter.models.ShelterEntity;

public interface ShelterRepository extends JpaRepository<ShelterEntity, Long> {
}
