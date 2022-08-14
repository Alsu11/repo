package ru.itis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByUserId(UUID id);

    void deleteByUserId(UUID id);
}
