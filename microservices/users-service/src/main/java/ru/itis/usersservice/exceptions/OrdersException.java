package ru.itis.usersservice.exceptions;

public class OrdersException extends ValidationException {
    public OrdersException(Errors error) {
        super(error);
    }
}
