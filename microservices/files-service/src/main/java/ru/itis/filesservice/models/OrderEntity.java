package ru.itis.filesservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

/**
 * Заказ
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders")
public class OrderEntity extends AbstractClassEntity {

    /** Смоимость */
    private int amount;

    /** Id пользователя */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /** Адрес выдачи заказа */
    @ManyToOne
    @JoinColumn(name = "address_id")
    private ShopAddressEntity shopAddress;

    ///** Список продуктов */
    //@ManyToMany
    //@JoinTable(name = "order_product",
    //        joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
    //        inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    //private Set<ProductEntity> products;
}
