package ru.shum.crudapp1.exception;

public class BookNotFoundException extends RuntimeException{
    private final String message = "Книги с указанным вами Id не существует";
}
