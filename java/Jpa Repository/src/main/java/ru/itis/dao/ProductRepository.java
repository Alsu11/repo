package ru.itis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
