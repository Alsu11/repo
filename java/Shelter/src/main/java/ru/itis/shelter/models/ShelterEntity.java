package ru.itis.shelter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "shelter")
public class ShelterEntity extends AbstractClassEntity {

    private String address;

    private Long numberOfCats;

    @OneToMany
    private Set<CatEntity> cats;
}
