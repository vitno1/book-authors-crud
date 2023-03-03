package ru.shum.crudapp1;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import ru.shum.crudapp1.dto.AuthorDTO;
import ru.shum.crudapp1.dto.BookDTO;
import ru.shum.crudapp1.entity.Book;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//import static org.assertj.core.api.Assertions.*;

public class BookDTOandAuthorDTOTest {
    private static Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void authorNameNotBlank() {
        AuthorDTO authorDTO = new AuthorDTO(ArgumentMatchers.anyLong(), "");
        Set<ConstraintViolation<AuthorDTO>> validate = validator.validate(authorDTO);
        Assertions.assertThat(validate.size()).isEqualTo(1);
    }

    @Test
    public void bookNameNotBlank() {
        List<AuthorDTO> list = new ArrayList<>();
        list.add(new AuthorDTO(ArgumentMatchers.anyLong(), "someName"));
        BookDTO bookDTO = new BookDTO(5L, "  ", list, 6);
        Set<ConstraintViolation<BookDTO>> validate = validator.validate(bookDTO);
        Assertions.assertThat(validate.size()).isEqualTo(1);
    }


}
