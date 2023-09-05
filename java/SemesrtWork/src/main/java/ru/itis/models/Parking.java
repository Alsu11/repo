package ru.itis.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking")
public class Parking extends AbstractEntity{

    @Column(unique = true, updatable = false)
    private String address;

    @Column(name = "price_for_hour")
    private Integer priceForHour;

    private Integer slotsCount;

}

