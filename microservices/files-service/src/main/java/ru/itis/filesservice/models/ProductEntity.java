package ru.itis.filesservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

/**
 * Продукт
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "products")
public class ProductEntity extends AbstractClassEntity {

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

    /** Идентификатор изображения товара */
    @Column(name = "picture_id")
    private UUID picture;

}
