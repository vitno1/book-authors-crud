package ru.shum.crudapp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shum.crudapp1.entity.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    Optional<Book> findById(Long id);
}
