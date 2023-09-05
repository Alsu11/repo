package ru.itis.filesservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.filesservice.models.UserEntity;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<UserEntity, UUID> {
}
