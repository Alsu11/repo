package ru.itis.usersservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Обратная связь
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "support")
public class SupportEntity extends AbstractClassEntity {

    /** Id пользователя */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /** Сообщение */
    private String message;
}
