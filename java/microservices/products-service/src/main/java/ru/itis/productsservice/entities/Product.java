package ru.itis.productservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    /** Назавание */
    private String name;

    /** Категория */
    private String category;

    /** Производитель */
    private String maker;

    /** Артикул */
    @Column(name = "vendor_code", nullable = false, unique = true)
    private String vendorCode;

    /** Стоимость */
    private Integer price;

    /** Описание */
    private String description;

    /** Скидка */
    private Integer discount;
}
