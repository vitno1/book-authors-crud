package ru.shum.crudapp1.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {
    private String name;
    private List<String> authors;
    private Integer passport;
}
