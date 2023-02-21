package ru.itis.usersservice.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.itis.usersservice.dto.enums.Role;
import ru.itis.usersservice.dto.enums.State;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Пользователь
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
@ToString(exclude = "supports")
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
    @Column(nullable = false)
    private String password;

    /** Скидка */
    private Integer discount;

    @Column(name = "avatar_id")
    private UUID avatar;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<SupportEntity> supports;

    @OneToMany(mappedBy = "user")
    private Set<CardEntity> cards;

    @ManyToMany
    @JoinTable(name = "liked",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<ProductEntity> liked;

}
