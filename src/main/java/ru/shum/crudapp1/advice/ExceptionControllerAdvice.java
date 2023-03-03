package ru.shum.crudapp1.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shum.crudapp1.exception.BookNotFoundException;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StringBuilder> notValidNameAndSizeAuthors(MethodArgumentNotValidException exception) {
        StringBuilder builder = new StringBuilder(exception.getFieldError().getField()).append(" ").append(exception.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> psqlExceptionHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Вы пытаетесь добавить уже существующие данные");
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> notFoundDataHandler(BookNotFoundException bookNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bookNotFoundException.getMessage());
    }
}
