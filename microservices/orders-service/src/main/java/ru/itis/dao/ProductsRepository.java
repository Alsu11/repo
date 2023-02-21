package ru.itis.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.models.ProductEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findById(UUID id);
}
