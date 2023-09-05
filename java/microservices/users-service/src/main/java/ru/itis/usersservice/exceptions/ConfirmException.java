package ru.itis.usersservice.exceptions;

public class ConfirmException extends ValidationException{
    public ConfirmException(Errors entity) {
        super(entity);
    }
}
