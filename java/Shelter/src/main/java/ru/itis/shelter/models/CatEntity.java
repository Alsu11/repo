package ru.itis.shelter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cats")
public class CatEntity extends AbstractClassEntity{

    public enum Health {
        ILL, HEALTH, UNCLEAR
    };

    public enum State {
        ON_HANDS, ON_SHELTER, ON_HOSPITAL
    };

    @Enumerated(value = EnumType.STRING)
    private Health health;

    @Enumerated(value = EnumType.STRING)
    private State state;

    private String name;

    private Long age;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterEntity shelter;
}
