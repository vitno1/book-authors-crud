package ru.shum.crudapp1.exception;

import java.util.function.Supplier;

public class BookNotFoundException extends RuntimeException implements Supplier<BookNotFoundException> {

    public BookNotFoundException(String message) {
        super(message);
    }


    @Override
    public BookNotFoundException get() {
        return this;
    }
}
