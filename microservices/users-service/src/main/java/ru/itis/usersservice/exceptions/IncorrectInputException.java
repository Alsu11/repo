package ru.itis.usersservice.exceptions;

public class IncorrectInputException extends ValidationException {
    public IncorrectInputException(Errors error) {
        super(error);
    }
}
