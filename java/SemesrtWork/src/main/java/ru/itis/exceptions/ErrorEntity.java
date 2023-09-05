package ru.itis.exceptions;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorEntity {
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

    // Парковка
    CAR_ALREADY_HERE(460, "Машина уже находиться здесь"),
    CAR_ARE_NOT_IN_THIS_PARKING(461, "Машины нет на этой парковке"),
    ADDRESS_WRONG(462, "Неверно введен адрес"),
    NO_PLACE(463, "Нет мест для парковки")
    ;

    int status;
    String message;

    ErrorEntity(int status, String message) {
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