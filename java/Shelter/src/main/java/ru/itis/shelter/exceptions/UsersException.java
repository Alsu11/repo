package ru.itis.shelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UsersException extends ValidationException {
    public UsersException(Errors entity) {
        super(entity);
    }
}
