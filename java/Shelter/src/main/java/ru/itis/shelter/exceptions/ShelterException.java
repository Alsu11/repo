package ru.itis.shelter.exceptions;

public class ShelterException extends ValidationException {
    public ShelterException(Errors entity) {
        super(entity);
    }
}
