package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "car_entity")
public class CarEntry extends AbstractEntity{

    private String model;

    @Column(unique = true)
    private String carNumber;

    private String color;
    private Instant startTime;
    private Instant endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;
}
