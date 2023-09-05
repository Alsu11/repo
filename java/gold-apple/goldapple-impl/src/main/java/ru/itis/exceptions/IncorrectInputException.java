package ru.itis.exceptions;

public class IncorrectInputException extends ValidationException {
    public IncorrectInputException(Errors error) {
        super(error);
    }
}
