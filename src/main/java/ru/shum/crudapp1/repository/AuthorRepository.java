package ru.shum.crudapp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shum.crudapp1.entity.Author;

public interface AuthorRepository extends JpaRepository<Author , Long> {
    public Author findAuthorByName(String name);
}
