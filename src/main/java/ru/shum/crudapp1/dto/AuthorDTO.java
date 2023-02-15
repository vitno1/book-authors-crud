package ru.shum.crudapp1.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthorDTO {
    private Long id;
    @NotBlank
    private String name;
}
