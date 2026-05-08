package org.jakubmiczek.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class BookTest {
    @Test
    public void shouldThrowExceptionWhenTitleIsEmpty() {
        assertThatThrownBy(() -> Book.builder().title("").build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Title cannot be empty");
    }

    @Test
    public void shouldThrowExceptionWhenIsbnIsEmpty() {
        assertThatThrownBy(() -> Book.builder().title("test").isbn("").build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ISBN cannot be empty");
    }

    @Test
    public void shouldCreateCorrectBook() {
        Book testBook = Book.builder().title("test").isbn("test").build();
        assertThat(testBook.getId()).isNotNull();
        assertThat(testBook.getTitle()).isEqualTo("test");
        assertThat(testBook.getIsbn()).isEqualTo("test");
    }
}
