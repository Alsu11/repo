package ru.itis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<UserEntity, UUID> {
}
