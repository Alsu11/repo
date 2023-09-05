package ru.itis.filesservice.exceptions;

public class NotFoundException extends ValidationException {
    public NotFoundException(Errors entity) {
        super(entity);
    }
}
