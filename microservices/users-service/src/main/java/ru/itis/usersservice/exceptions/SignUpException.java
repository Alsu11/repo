package ru.itis.usersservice.exceptions;

public class SignUpException extends ValidationException{
    public SignUpException(Errors entity) {
        super(entity);
    }
}
