package ru.itis.exceptions;

public class NotFoundException extends ValidationException {
    public NotFoundException(ErrorEntity entity) {
        super(entity);
    }
}
