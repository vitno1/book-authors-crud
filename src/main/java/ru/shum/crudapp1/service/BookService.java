package ru.shum.crudapp1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.shum.crudapp1.dto.BookDTO;
import ru.shum.crudapp1.entity.Author;
import ru.shum.crudapp1.entity.Book;
import ru.shum.crudapp1.entity.Passport;
import ru.shum.crudapp1.exception.BookNotFoundException;
import ru.shum.crudapp1.repository.AuthorRepository;
import ru.shum.crudapp1.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    private void checkDataForAdd(BookDTO bookDTO) {
        if (bookDTO.getAuthors().isEmpty()) {
            throw new IllegalArgumentException("У книги должен быть хотябы один автор");
        } else if (bookRepository.exists(exampleByBookName(bookDTO)) || bookDTO.getName().isBlank()) {
            throw new IllegalArgumentException("Данная книга уже есть в бд или вы ввели пустое значение");
        } else {
            for (String name : bookDTO.getAuthors()) {
                if (authorRepository.exists(exampleByAuthorName(name)) || name.isBlank()) {
                    Author author = authorRepository.findAuthorByName(name);
                    throw new IllegalArgumentException(author + " уже есть в БД, а у одного автора может быть только одна книга или вы ввели пустое значение");
                }
            }
        }
    }

    private void checkDataForUpdate(BookDTO bookDTO, Book book) {
        if (!bookDTO.getName().equals(book.getName())) {
            if (bookRepository.exists(exampleByBookName(bookDTO))) {
                throw new IllegalArgumentException("Книга с таким названием уже есть в БД");
            } else if (bookDTO.getName().isBlank()) {
                throw new IllegalArgumentException("Название книги не может быть пустым");
            }
        }


    }

    public void addBook(BookDTO bookDTO) {
        checkDataForAdd(bookDTO);
        Book book = new Book();
        for (String name : bookDTO.getAuthors()) {
            Author author = new Author();
            author.setName(name);
            book.getAuthors().add(author);
            System.out.println("Создали данного автора " + author);
        }
        Passport passport = new Passport();
        passport.setNumber(bookDTO.getPassport());
        book.setPassport(passport);
        book.setName(bookDTO.getName());
        bookRepository.save(book);
        log.info("Имя книги " + book.getName() + " авторы книги " + book.getAuthors() +
                " Пасспорт книги " + book.getPassport() + " успешно добавлены в БД");
    }

    private Example<Author> exampleByAuthorName(String name) {
        Example<Author> example = new Example<>() {
            @Override
            public Author getProbe() {
                Author author = new Author();
                author.setName(name);
                return author;
            }

            @Override
            public ExampleMatcher getMatcher() {
                return ExampleMatcher.matching();
            }
        };
        return example;
    }

    private Example<Book> exampleByBookName(BookDTO bookDTO) {
        Example<Book> example = new Example<>() {
            @Override
            public Book getProbe() {
                Book book = new Book();
                book.setName(bookDTO.getName());
                return book;
            }

            @Override
            public ExampleMatcher getMatcher() {
                return ExampleMatcher.matching();
            }
        };
        return example;
    }

    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        List<String> authors = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            String name = author.getName();
            authors.add(name);
        }
        BookDTO bookDTO = new BookDTO(book.getName(), authors, book.getPassport().getNumber());
        return bookDTO;
    }

    public void updateBook(BookDTO bookDTO, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        log.info("Данные о книге перед изменением - Имя книги " + book.getName() + " авторы книги " + book.getAuthors() +
                " Пасспорт книги " + book.getPassport() + " успешно добавлены в БД");
        book.setName(bookDTO.getName());
        Passport passport = new Passport();
        passport.setNumber(bookDTO.getPassport());
        book.setPassport(passport);
        for (String name : bookDTO.getAuthors()) {
            Author author = new Author();
            author.setName(name);
            book.getAuthors().add(author);
        }
        bookRepository.save(book);
        log.info("Данные о книге успешно изменены на - Имя книги " + book.getName() + " авторы книги " + book.getAuthors() +
                " Пасспорт книги " + book.getPassport() + " успешно добавлены в БД");
    }


}


//    public void addBook(BookDTO bookDTO) {
//        if (bookDTO.getAuthors().isEmpty()) {
//            System.out.println("У книги должен быть хотябы один автор");
//        } else if (bookRepository.exists(exampleByBookName(bookDTO))) {
//            System.out.println("Данная книга уже есть в бд");
//        } else {
//            boolean correctAuthors = true;
//            for (String name : bookDTO.getAuthors()) {
//                if (authorRepository.exists(exampleByAuthorName(name)) || name.isBlank()) {
//                    Author author = authorRepository.findAuthorByName(name);
//                    System.out.println(author + " уже есть в БД, а у одного автора может быть только одна книга или вы ввели пустое значение");
//                    correctAuthors = false;
//                    break;
//                }
//            }
//            if (correctAuthors) {
//                Book book = new Book();
//                for (String name : bookDTO.getAuthors()) {
//                    Author author = new Author();
//                    author.setName(name);
//                    book.getAuthors().add(author);
//                    System.out.println("Создали данного автора " + author);
//                    Passport passport = new Passport();
//                    passport.setNumber(bookDTO.getPassport());
//                    book.setPassport(passport);
//                    book.setName(bookDTO.getName());
//                }
//                bookRepository.save(book);
//                log.info("Имя книги " + book.getName() + " авторы книги " + book.getAuthors() +
//                        " Пасспорт книги " + book.getPassport() + " успешно добавлены в БД");
//            }
//        }
//
//    }
