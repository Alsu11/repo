package ru.itis.filesservice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Errors {
    // Общие ошибки
    INVALID_REQUEST(400, "Неверный запрос"),
    INVALID_TOKEN(403, "Ошибка авторизации"),
    NOT_FOUND(404, "Не найдено"),

    // Файлы
    NO_FILE(470, "Файл отсутствует"),
    INVALID_FILE_TYPE(471, "Недопустимый тип файла, тип должен быть либо png, либо jpeg"),
    INVALID_FILE(472, "Файл недействителен"),
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

}
