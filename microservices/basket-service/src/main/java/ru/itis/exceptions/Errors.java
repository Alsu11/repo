package ru.itis.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Errors {
    // Общие ошибки
    INVALID_REQUEST(400, "Неверный запрос"),
    INVALID_TOKEN(403, "Ошибка авторизации"),
    NOT_FOUND(404, "Не найдено"),
    EMAIL_ALREADY_TAKEN(453, "Email уже занят"),

    // Регистрация
    PASSWORD_TOO_SHORT(460, "Пароль слишком короткий"),
    INVALID_EMAIL(461, "Некорректный Email"),

    // Вход
    USER_NOT_FOUND(404, "Пользователь не найден"),
    INCORRECT_PASSWORD(450, "Неверный пароль"),

    // Файлы
    NO_FILE(470, "Файл отсутствует"),
    INVALID_FILE_TYPE(471, "Недопустимый тип файла, тип должен быть либо png, либо jpeg"),
    INVALID_FILE(472, "Файл недействителен"),

    //Заказы
    ORDERS_DOES_NOT_EXIST(480, "Заказы не найдены"),

    //Продукты
    PRODUCT_DOES_NOT_EXIST(490, "Продукт не найден"),

    // Подтверждение по почте
    INVALID_CONFIRM_CODE(470, "Код подтверждения не подходит"),
    CODE_TIME_OUT(471, "Код не активен, время вышло"),

    //Токены
    TOKEN_NOT_FOUND(700, "Токен не найден"),
    EXPIRED_TOKEN(701, "Время жизни токена истекло")
    ;
    int status;
    String message;

    Errors(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String rawValue() {
        return toString();
    }
}
