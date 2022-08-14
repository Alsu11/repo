package ru.itis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.models.ProductEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {
    Page<ProductEntity> findAllByName(String name, Pageable pageable);
    Set<ProductEntity> findAllByCategory(String category);

    @Query("select p from ProductEntity p where p.discount > 0")
    Set<ProductEntity> findAllWithDiscount();
}
