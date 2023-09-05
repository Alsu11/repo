package ru.itis.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.dto.ShopAddressDto;
import ru.itis.models.ShopAddressEntity;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ShopAddressMapper {
    ShopAddressEntity toRequest(ShopAddressDto shopAddressDto);
}
