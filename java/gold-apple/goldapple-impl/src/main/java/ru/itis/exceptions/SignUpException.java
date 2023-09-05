package ru.itis.exceptions;

public class SignUpException extends ValidationException{
    public SignUpException(Errors entity) {
        super(entity);
    }
}
