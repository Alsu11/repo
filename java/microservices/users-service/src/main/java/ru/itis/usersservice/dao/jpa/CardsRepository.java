package ru.itis.usersservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.usersservice.models.CardEntity;

import java.util.UUID;

public interface CardsRepository extends JpaRepository<CardEntity, UUID> {
}
