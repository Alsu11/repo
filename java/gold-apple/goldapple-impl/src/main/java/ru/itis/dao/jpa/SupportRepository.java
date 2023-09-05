package ru.itis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dto.SupportDto;
import ru.itis.models.SupportEntity;

public interface SupportRepository extends JpaRepository<SupportEntity, Long> {
}
