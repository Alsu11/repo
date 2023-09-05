package ru.itis.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.UserDto;
import ru.itis.models.UserEntity;
import ru.itis.security.details.UserInfo;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UsersMapper {
    UserDto toResponse(UserEntity user);
    UserEntity toRequest(SignUpForm signUpForm);
    UserInfo toRequest(UserEntity user);

}
