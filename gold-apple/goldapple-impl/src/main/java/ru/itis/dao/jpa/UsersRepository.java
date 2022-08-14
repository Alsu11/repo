package ru.itis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.UserEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserEntityByConfirmCode(String code);
    Optional<UserEntity> findUserEntityByEmail(String email);
}
