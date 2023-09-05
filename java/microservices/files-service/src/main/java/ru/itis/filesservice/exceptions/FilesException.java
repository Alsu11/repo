package ru.itis.filesservice.exceptions;

public class FilesException extends ValidationException {
    public FilesException(Errors error) {
        super(error);
    }
}
