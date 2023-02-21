package ru.itis.usersservice.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.itis.usersservice.dto.SignUpForm;
import ru.itis.usersservice.dto.UserDto;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.security.details.UserInfo;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UsersMapper {
    UserDto toResponse(UserEntity user);

    UserEntity toRequest(SignUpForm signUpForm);

    UserInfo toRequest(UserEntity user);
}
