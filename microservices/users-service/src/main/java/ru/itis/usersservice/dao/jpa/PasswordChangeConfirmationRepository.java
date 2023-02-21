package ru.itis.usersservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.usersservice.models.PasswordChangeConfirmationClassEntity;

import java.util.Optional;
import java.util.UUID;

public interface PasswordChangeConfirmationRepository extends JpaRepository<PasswordChangeConfirmationClassEntity, UUID> {

    Optional<PasswordChangeConfirmationClassEntity> findByToken(String token);

}
