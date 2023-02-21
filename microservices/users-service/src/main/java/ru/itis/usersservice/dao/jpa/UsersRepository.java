package ru.itis.usersservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.usersservice.models.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserEntityByEmail(String email);

}
