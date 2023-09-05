package ru.itis.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.dto.OrderDto;
import ru.itis.models.OrderEntity;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrdersMapper {
    OrderDto toResponse(OrderEntity order);
}
