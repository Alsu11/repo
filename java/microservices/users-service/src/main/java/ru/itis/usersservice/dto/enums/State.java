package ru.itis.usersservice.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {

    NOT_CONFIRMED("Не авторизован"),
    CONFIRMED("Авторизоан"),
    DELETED("Удален"),
    BANNED("Заблокирован")
    ;

    private final String description;
}
