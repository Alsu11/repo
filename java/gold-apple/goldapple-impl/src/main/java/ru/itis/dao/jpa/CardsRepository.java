package ru.itis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.CardEntity;

import java.util.UUID;

public interface CardsRepository extends JpaRepository<CardEntity, UUID> {
}
