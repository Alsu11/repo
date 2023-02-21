package ru.itis.filesservice.models;

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
@Table(name = "card")
public class CardEntity extends AbstractClassEntity {

    /** Номер карты */
    @Column(nullable = false, unique = true)
    private String number;

    /** Срок действия */
    @Column(nullable = false)
    private String expiration;

    /** Трехзначное значение на обороте карты */
    private String cvv;

    /** Имя владельца */
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    /** Id владельца */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
