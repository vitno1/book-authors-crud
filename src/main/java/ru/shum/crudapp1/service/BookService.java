package ru.shum.crudapp1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shum.crudapp1.dto.AuthorDTO;
import ru.shum.crudapp1.dto.BookDTO;
import ru.shum.crudapp1.entity.Author;
import ru.shum.crudapp1.entity.Book;
import ru.shum.crudapp1.entity.Passport;
import ru.shum.crudapp1.exception.BookNotFoundException;
import ru.shum.crudapp1.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    public void addBook(BookDTO bookDTO) {
        List<Author> authors = new ArrayList<>();
        for (AuthorDTO authorDTO : bookDTO.getAuthors()) {
            Author author = new Author();
            author.setName(authorDTO.getName());
            authors.add(author);
        }
        Passport passport = new Passport();
        passport.setNumber(bookDTO.getPassport());
        Book book = Book.builder().authors(authors).passport(passport).name(bookDTO.getName()).build();
        bookRepository.save(book);
        log.info("Имя книги " + book.getName() + " авторы книги " + book.getAuthors() +
                " Пасспорт книги " + book.getPassport() + " успешно добавлены в БД");
    }


    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        List<AuthorDTO> authors = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            String name = author.getName();
            Long authorId = author.getId();
            AuthorDTO authorDTO = new AuthorDTO(authorId, name);
            authors.add(authorDTO);
        }
        return BookDTO.builder().id(book.getId()).name(book.getName()).authors(authors).passport(book.getPassport().getNumber()).build();
    }

    public void updateBook(BookDTO bookDTO, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setName(bookDTO.getName());
        book.getPassport().setNumber(bookDTO.getPassport());
        for (AuthorDTO authorDTO : bookDTO.getAuthors()) {
            for (Author author : book.getAuthors()) {
                if (authorDTO.getId().equals(author.getId())) {
                    author.setName(authorDTO.getName());
                }
            }
        }
        bookRepository.save(book);
        log.info("Данные о книге успешно изменены на - Имя книги " + book.getName() + " авторы книги " + book.getAuthors() +
                " Пасспорт книги " + book.getPassport() + " успешно добавлены в БД");
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

