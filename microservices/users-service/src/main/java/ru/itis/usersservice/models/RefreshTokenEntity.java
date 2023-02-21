package ru.itis.usersservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class RefreshTokenEntity extends AbstractClassEntity {

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Instant expireDate;

}
