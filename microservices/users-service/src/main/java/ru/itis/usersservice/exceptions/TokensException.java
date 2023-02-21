package ru.itis.usersservice.exceptions;

public class TokensException extends ValidationException {
    public TokensException(Errors error) {
        super(error);
    }
}
