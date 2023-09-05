package ru.itis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.OrderEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<Set<OrderEntity>> findAllByUser(UUID id);
}
