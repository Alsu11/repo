package ru.itis.usersservice.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * Банковские карты пользователей
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "cards")
public class CardEntity extends AbstractClassEntity {

    /** Данные карты */
    @Column(nullable = false, unique = true)
    private String data;

    /** Id владельца */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
