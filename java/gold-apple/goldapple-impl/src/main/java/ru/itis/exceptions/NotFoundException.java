package ru.itis.exceptions;

public class NotFoundException extends ValidationException {
    public NotFoundException(Errors error) {
        super(error);
    }
}
