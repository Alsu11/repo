package ru.itis.shelter.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.shelter.models.CatEntity;

public interface CatsRepository extends JpaRepository<CatEntity, Long> {
    Page<CatEntity> findAll(Pageable pageable);
}
