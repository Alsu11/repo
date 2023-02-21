package ru.itis.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.dto.ProductDto;
import ru.itis.models.ProductEntity;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductsMapper {
    ProductDto toResponse(ProductEntity productEntity);
    Set<ProductDto> toResponse(Set<ProductEntity> productEntitySet);
    List<ProductDto> toResponse(List<ProductEntity> productEntityList);
}
