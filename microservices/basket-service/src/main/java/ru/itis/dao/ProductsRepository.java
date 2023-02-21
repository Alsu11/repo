package ru.itis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.ProductEntity;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {
}
