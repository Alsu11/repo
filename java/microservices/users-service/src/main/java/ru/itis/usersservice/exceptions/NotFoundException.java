package ru.itis.usersservice.exceptions;

public class NotFoundException extends ValidationException {
    public NotFoundException(Errors error) {
        super(error);
    }
}
