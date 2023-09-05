package ru.itis.usersservice.exceptions;

public class UsersException extends ValidationException{
    public UsersException(Errors error) {
        super(error);
    }
}
