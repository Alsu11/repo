package ru.itis.usersservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.usersservice.models.SupportEntity;

public interface SupportRepository extends JpaRepository<SupportEntity, Long> {
}
