package ru.itis.filesservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.filesservice.models.ProductEntity;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {

}
