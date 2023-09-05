package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.dto.enums.Role;
import ru.itis.dto.enums.State;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Пользователь
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class UserEntity extends AbstractClassEntity {

    /** Имя */
    @Column(name = "first_name")
    private String firstName;

    /** Фамилия */
    @Column(name = "last_name")
    private String lastName;

    /** Логин */
    @Column(nullable = false, unique = true)
    private String email;

    /** Номер телефона */
    @Column(name = "phone_number")
    private String phoneNumber;

    /** Захешированный пароль */
    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    /** Скидка */
    private Integer discount;

    /** Код для подтверждения */
    @Column(name = "confirm_code")
    private String confirmCode;

    @Column(name = "avatar_id")
    private UUID avatar;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<SupportEntity> supports;

    @OneToMany(mappedBy = "user")
    private Set<OrderEntity> orders;

    @OneToMany(mappedBy = "user")
    private Set<CardEntity> cards;

    @ManyToMany
    @JoinTable(name = "liked",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<ProductEntity> liked;

    @ManyToMany
    @JoinTable(name = "basket",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<ProductEntity> basket;

}
