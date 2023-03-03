package ru.shum.crudapp1;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import ru.shum.crudapp1.dto.AuthorDTO;
import ru.shum.crudapp1.dto.BookDTO;
import ru.shum.crudapp1.entity.Author;
import ru.shum.crudapp1.entity.Book;
import ru.shum.crudapp1.entity.Passport;
import ru.shum.crudapp1.repository.BookRepository;
import ru.shum.crudapp1.service.BookService;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class BookServiceTest {
    @InjectMocks
    BookService bookService;
    @Mock
    BookRepository bookRepository;

    @Test
    public void successAddedBook() {
        Author author = new Author();
        author.setName("someName");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        Passport passport = new Passport();
        passport.setNumber(15);
        Book book = Book.builder().authors(authors).passport(passport).name("someName").build();
        log.info("book variable is =" + book);
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        AuthorDTO authorDTO = new AuthorDTO(null, "someName");
        authorDTOS.add(authorDTO);
        BookDTO bookDTO = BookDTO.builder().name("someName").authors(authorDTOS).passport(15).build();
        log.info("bookDTO variable is =" + bookDTO);
        Passport returnPassport = new Passport();
        returnPassport.setId(1L);
        returnPassport.setNumber(15);
        List<Author> authorList = new ArrayList<>();
        Author author1 = new Author(1L, "someName");
        authorList.add(author1);
        Book returnBook = Book.builder().id(1L).name("someName").passport(returnPassport).authors(authorList).build();
        log.info("returnBook is =" + returnBook);
        BDDMockito.when(bookRepository.save(ArgumentMatchers.eq(book))).thenReturn(returnBook);

        bookService.addBook(bookDTO);

        Assertions.assertThat(bookRepository.save(book)).isEqualTo(returnBook);
    }

    @Test
    public void correctGetBook() {
        Passport returnPassport = new Passport();
        returnPassport.setId(1L);
        returnPassport.setNumber(15);
        List<Author> authorList = new ArrayList<>();
        Author author1 = new Author(1L, "someName");
        authorList.add(author1);
        Book returnBook = Book.builder().id(1L).name("someName").passport(returnPassport).authors(authorList).build();
        BDDMockito.given(bookRepository.findById(returnBook.getId())).willReturn(Optional.of(returnBook));

        bookService.findBookById(1L);

        Assertions.assertThat(bookRepository.findById(1L)).isEqualTo(Optional.of(returnBook));
    }


}

