package ru.itis.usersservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.usersservice.models.EmailConfirmationClassEntity;

import java.util.Optional;
import java.util.UUID;

public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmationClassEntity, UUID> {

    Optional<EmailConfirmationClassEntity> findByToken(String token);


}
