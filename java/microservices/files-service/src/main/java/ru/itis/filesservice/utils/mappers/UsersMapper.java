package ru.itis.filesservice.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.filesservice.dto.UserDto;
import ru.itis.filesservice.models.UserEntity;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UsersMapper {
    UserDto toResponse(UserEntity user);
}
