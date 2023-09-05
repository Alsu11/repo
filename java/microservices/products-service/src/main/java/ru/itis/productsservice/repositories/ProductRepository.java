package ru.itis.productservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.productservice.entities.Product;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByNameStartsWith(String name, Pageable pagination);

    Page<Product> findAllByCategoryStartsWith(String category, Pageable pagination);

    @Query("SELECT p FROM Product p WHERE p.discount > 0")
    Page<Product> findAllByDiscount(Pageable pagination);
}
