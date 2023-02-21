package ru.itis.usersservice.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.usersservice.models.JwtToken;

import java.util.Optional;
import java.util.UUID;

public interface BlackListRepository extends MongoRepository<JwtToken, UUID> {
    Optional<JwtToken> findByJwt(String token);
}
