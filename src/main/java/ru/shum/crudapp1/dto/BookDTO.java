package ru.shum.crudapp1.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotEmpty
    @Valid
    private List<AuthorDTO> authors;
    private Integer passport;
}
