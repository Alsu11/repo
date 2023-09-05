package ru.itis.exceptions;

public class NoPlaceException extends ValidationException {
    public NoPlaceException(ErrorEntity entity) {
        super(entity);
    }
}
