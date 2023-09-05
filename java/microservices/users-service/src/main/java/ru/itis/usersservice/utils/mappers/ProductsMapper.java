package ru.itis.usersservice.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.usersservice.dto.ProductDto;
import ru.itis.usersservice.models.ProductEntity;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductsMapper {
    ProductDto toResponse(ProductEntity productEntity);
    Set<ProductDto> toResponse(Set<ProductEntity> productEntitySet);
    List<ProductDto> toResponse(List<ProductEntity> productEntityList);
}
