package ru.itis.usersservice.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@ToString
@Table(name = "password_change_confirmation_token")
public class PasswordChangeConfirmationClassEntity extends AbstractClassEntity {

    @Column(name = "token")
    private String token;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private UserEntity user;

    @Column
    private String newPassword;

    @Column
    Boolean confirmed = false;
}
