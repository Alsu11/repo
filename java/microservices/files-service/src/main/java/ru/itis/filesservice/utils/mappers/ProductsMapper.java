package ru.itis.filesservice.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.filesservice.dto.ProductDto;
import ru.itis.filesservice.models.ProductEntity;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductsMapper {
    ProductDto toResponse(ProductEntity productEntity);
}
