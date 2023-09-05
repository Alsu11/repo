package ru.itis.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.dto.SupportDto;
import ru.itis.models.SupportEntity;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SupportMapper {
    SupportDto toResponse(SupportEntity support);
}
