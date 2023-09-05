package ru.itis.usersservice.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.usersservice.dto.SupportDto;
import ru.itis.usersservice.models.SupportEntity;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SupportMapper {
    SupportDto toResponse(SupportEntity support);
}
