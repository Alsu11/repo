package ru.itis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.BasketEntity;

import java.util.List;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<BasketEntity, UUID> {
    List<BasketEntity> findAllByUserId(UUID userId);
    void deleteByUserIdAndProductId(UUID userId, UUID productId);
}
