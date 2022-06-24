package ru.itis.shelter.services;

import ru.itis.shelter.dto.ConfirmDto;
import ru.itis.shelter.dto.SignUpForm;
import ru.itis.shelter.dto.UserDto;

public interface UsersService {
    ConfirmDto signUp(SignUpForm signUpForm);

    UserDto confirm(String confirmCode);

}
