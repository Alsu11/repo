package ru.itis.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.productservice.entities.Product;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Продукт")
public class ProductDto {

    @Schema(description = "ID продукта", example = "62aa950c-8f70-4818-ab26-1a90b3026cfc")
    private UUID productId;

    @Schema(description = "Название", example = "Сыр")
    private String name;

    @Schema(description = "Категория", example = "Молочная продукция")
    private String category;

    @Schema(name = "Марка", example = "Простоквашино")
    private String maker;

    @Schema(name = "Артикул", example = "RK-26880")
    private String vendorCode;

    @Schema(name = "Цена", example = "123")
    private Integer price;

    @Schema(name = "Описание", example = "кбжу: 12, 13, 14")
    private String description;

    @Schema(name = "Скидка", example = "12")
    private Integer discount;


    public static List<ProductDto> from(List<Product> products) {
        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public static ProductDto from(Product product){
        return ProductDto.builder()
                .category(product.getCategory())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .productId(product.getId())
                .maker(product.getMaker())
                .price(product.getPrice())
                .name(product.getName())
                .vendorCode(product.getVendorCode())
                .build();
    }
}
