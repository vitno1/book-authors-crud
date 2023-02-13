package ru.shum.crudapp1.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", unique = true)
    private String name;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private List<Author> authors = new ArrayList<>();

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Passport passport;


}
