package ru.itis.mappers;

import org.mapstruct.Mapper;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.UserDto;
import ru.itis.models.User;

@Mapper
public interface UserMapper {
    UserDto toResponse(User user);
    User toRequest(SignUpForm signUpForm);
}
