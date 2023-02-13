package ru.shum.crudapp1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shum.crudapp1.dto.BookDTO;
import ru.shum.crudapp1.service.BookService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CrudController {
    private final BookService bookService;

    @PostMapping("/book/add")
    public void addBook(@RequestBody BookDTO bookDTO) {
        bookService.addBook(bookDTO);

    }

    @GetMapping("/book/get/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        BookDTO book = bookService.findBookById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
    }

    @PutMapping("/book/update/{id}")
    public void updateBook(@RequestBody BookDTO bookDTO, @PathVariable Long id) {
        bookService.updateBook(bookDTO, id);
    }

    @DeleteMapping("/book/delete")
    public void deleteBook(@RequestBody BookDTO bookDTO) {

    }
}
