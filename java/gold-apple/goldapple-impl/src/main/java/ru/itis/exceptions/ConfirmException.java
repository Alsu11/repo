package ru.itis.exceptions;

public class ConfirmException extends ValidationException{
    public ConfirmException(Errors entity) {
        super(entity);
    }
}
