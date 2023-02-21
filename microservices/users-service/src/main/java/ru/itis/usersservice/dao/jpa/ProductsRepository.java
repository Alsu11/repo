package ru.itis.usersservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.usersservice.models.ProductEntity;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {
}
