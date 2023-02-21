package ru.itis.exceptions;

public class OrdersException extends ValidationException {
    public OrdersException(Errors error) {
        super(error);
    }
}
