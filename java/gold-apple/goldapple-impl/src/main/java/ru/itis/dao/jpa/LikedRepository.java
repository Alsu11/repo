package ru.itis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.LikedEntity;

import java.util.List;
import java.util.UUID;

public interface LikedRepository extends JpaRepository<LikedEntity, UUID> {
    List<LikedEntity> findAllByUserId(UUID userId);

    void deleteByUserIdAndProductId(UUID userId, UUID productId);
}
