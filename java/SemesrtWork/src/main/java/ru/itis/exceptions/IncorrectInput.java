package ru.itis.exceptions;

public class IncorrectInput extends ValidationException {
    public IncorrectInput(ErrorEntity entity) {
        super(entity);
    }
}
