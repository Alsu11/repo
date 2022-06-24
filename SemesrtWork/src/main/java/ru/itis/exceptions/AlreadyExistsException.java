package ru.itis.exceptions;

public class AlreadyExistsException extends ValidationException {
    public AlreadyExistsException(ErrorEntity entity) {
        super(entity);
    }
}
