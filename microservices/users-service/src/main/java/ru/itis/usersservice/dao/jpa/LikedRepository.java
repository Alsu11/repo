package ru.itis.usersservice.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.usersservice.models.LikedEntity;

import java.util.List;
import java.util.UUID;

public interface LikedRepository extends JpaRepository<LikedEntity, UUID> {
    List<LikedEntity> findAllByUserId(UUID userId);

    @Modifying
    @Query("delete from LikedEntity l where l.userId=:userId and l.productId = :productId")
    void deleteByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);

}
