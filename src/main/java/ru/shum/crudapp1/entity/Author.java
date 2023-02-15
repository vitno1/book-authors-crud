package ru.shum.crudapp1.entity;



import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "author")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", unique = true)
    private String name;

}
