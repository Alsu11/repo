package ru.itis.usersservice.services;

import ru.itis.usersservice.dto.SignUpForm;
import ru.itis.usersservice.dto.UserDto;

public interface UsersService {
    UserDto signUp(SignUpForm signUpForm);
}
