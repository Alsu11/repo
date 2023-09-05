package ru.itis.shelter.exceptions;

public class CatsException extends ValidationException {
    public CatsException(Errors entity) {
        super(entity);
    }
}
