package org.jakubmiczek.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


public class BookTest {
    @Test
    public void shouldThrowExceptionWhenTitleIsEmpty() {
        assertThatThrownBy(() -> Book.builder().title("").build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[SYSTEM]: Title cannot be empty");
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsNull() {
        assertThatThrownBy(() -> Book.builder().title(null).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[SYSTEM]: Title cannot be empty");
    }

    @Test
    public void shouldThrowExceptionWhenIsbnIsEmpty() {
        assertThatThrownBy(() -> Book.builder().title("test").isbn("").build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[SYSTEM]: ISBN cannot be empty");
    }

    @Test
    public void shouldThrowExceptionWhenIsbnIsNull() {
        assertThatThrownBy(() -> Book.builder().title("test").isbn(null).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[SYSTEM]: ISBN cannot be empty");
    }

    @Test
    public void shouldThrowExceptionWhenPublishDateIsInvalid() {
        assertThatThrownBy(() -> Book.builder().title("test").isbn("test").publishDate(LocalDate.parse("2028-04-03")).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[SYSTEM]: Publish date cannot be after now");
    }

    @Test
    public void shouldCreateCorrectBook() {
        Book testBook = Book.builder().title("test").isbn("test").publishDate(LocalDate.now()).build();
        assertThat(testBook.getId()).isNotNull();
        assertThat(testBook.getTitle()).isEqualTo("test");
        assertThat(testBook.getIsbn()).isEqualTo("test");
    }

    @Test
    void shouldGenerateUUIDWhenIdIsNull() {
        Book book = Book.builder().title("test").isbn("test").publishDate(LocalDate.now()).build();

        assertThat(book.getId()).isNotNull();
    }

    @Test
    void shouldUseProvidedUUID() {
        UUID uuid = UUID.randomUUID();

        Book book = Book.builder().id(uuid).title("test").isbn("test").publishDate(LocalDate.now()).build();

        assertThat(book.getId()).isEqualTo(uuid);
    }
}
