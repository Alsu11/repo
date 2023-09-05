package ru.itis.filesservice.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final Errors entity;

    public ValidationException(Errors entity) {
        super(entity.getMessage());
        this.entity = entity;
    }

    public ValidationException(String entityRawValue) {
        super(entityRawValue);
        this.entity = Errors.valueOf(entityRawValue);
    }

}
