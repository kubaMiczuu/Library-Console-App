package org.jakubmiczek.service;

import org.jakubmiczek.exception.BookAlreadyExistException;
import org.jakubmiczek.model.Book;
import org.jakubmiczek.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    public void shouldThrowBookAlreadyExistExceptionWhenIsbnExists() {
        String  isbn = "123-456-7890";

        Book newBook = Book.builder().title("newBook").isbn(isbn).build();
        Book existingBook = Book.builder().title("existingBook").isbn(isbn).build();

        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(existingBook));

        assertThatThrownBy(() -> libraryService.addBook(newBook))
                .isInstanceOf(BookAlreadyExistException.class)
                .hasMessage("Book with ISBN " + isbn + " already exists");

        verify(bookRepository, never()).save(any());
    }
}
