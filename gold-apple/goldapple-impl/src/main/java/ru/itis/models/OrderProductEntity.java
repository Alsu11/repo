package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Продукт в заказе
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "order_product")
public class OrderProductEntity extends AbstractClassEntity {

    /** Id заказа */
    @Column(name = "order_id")
    private UUID orderId;

    /** Id продукта */
    @Column(name = "product_id")
    private UUID productId;
}
