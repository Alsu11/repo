package ru.itis.usersservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Избранное пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "liked")
public class LikedEntity extends AbstractClassEntity {

    /** Id пользователя */
    @Column(name = "user_id")
    private UUID userId;

    /** Id продукта */
    @Column(name = "product_id")
    private UUID productId;

}
