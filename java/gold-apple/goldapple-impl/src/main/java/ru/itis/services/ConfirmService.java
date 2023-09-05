package ru.itis.services;

import ru.itis.dto.UserDto;

public interface ConfirmService {
    UserDto confirmAccount(String code);
}
