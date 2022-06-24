package ru.itis.shelter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.shelter.models.UserEntity;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByConfirmCode(String confirmCode);

}
