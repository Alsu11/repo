package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Адрес магазина
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "shop_address")
public class ShopAddressEntity extends AbstractClassEntity {

    /** Адрес */
    private String address;

    @OneToMany(mappedBy = "shopAddress")
    private Set<OrderEntity> orders;
}
