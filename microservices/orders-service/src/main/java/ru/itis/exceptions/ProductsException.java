package ru.itis.exceptions;

public class ProductsException extends ValidationException {
    public ProductsException(Errors error) {
        super(error);
    }
}
