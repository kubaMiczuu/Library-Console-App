package org.jakubmiczek.service;

import org.jakubmiczek.exception.BookAlreadyExistException;
import org.jakubmiczek.exception.BookNotFoundException;
import org.jakubmiczek.model.Book;
import org.jakubmiczek.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    public void shouldThrowBookAlreadyExistExceptionWhenIsbnExists() {

        String isbn = "123-456-7890";
        Book newBook = Book.builder().title("newBook").isbn(isbn).publishDate(LocalDate.now()).build();
        Book existingBook = Book.builder().title("existingBook").isbn(isbn).publishDate(LocalDate.now()).build();

        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(existingBook));

        assertThatThrownBy(() -> libraryService.addBook(newBook))
                .isInstanceOf(BookAlreadyExistException.class)
                .hasMessage("[SYSTEM]: Book with ISBN: " + isbn + " already exists");

        verify(bookRepository, never()).save(any());
    }

    @Test
    public void shouldAddNewBook() {
        String  isbn = "123-456-7890";
        Book newBook = Book.builder().title("newBook").isbn(isbn).publishDate(LocalDate.now()).build();

        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        libraryService.addBook(newBook);

        verify(bookRepository, times(1)).save(newBook);
    }

    @Test
    public void shouldThrowBookNotFoundExceptionWhenIsbnDoesNotExist() {
        String  isbn = "123-456-7890";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> libraryService.deleteBook(isbn))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("[SYSTEM]: Book with ISBN: "+isbn+ " not found");
    }

    @Test
    public void shouldDeleteBook() {
        String  isbn = "123-456-7890";
        Book newBook = Book.builder().title("newBook").isbn(isbn).publishDate(LocalDate.now()).build();

        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(newBook));
        libraryService.deleteBook(isbn);

        verify(bookRepository, times(1)).deleteByIsbn(isbn);
    }

    @Test
    public void shouldReturnListOfBooksBasedOnTitleFragment() {
        String fragment = "cat";

        Book newBook1 = Book.builder().title("cat").isbn("test1").publishDate(LocalDate.now()).build();
        Book newBook2 = Book.builder().title("Book").isbn("test2").publishDate(LocalDate.now()).build();
        Book newBook3 = Book.builder().title("BookCat").isbn("test3").publishDate(LocalDate.now()).build();

        when(bookRepository.findAll()).thenReturn(Arrays.asList(newBook1, newBook2, newBook3));

        assertThat(libraryService.search(fragment)).hasSize(2);
    }

    @Test
    public void shouldReturnAllBooks() {
        Book newBook1 = Book.builder().title("cat").isbn("test1").publishDate(LocalDate.now()).build();
        Book newBook2 = Book.builder().title("Book").isbn("test2").publishDate(LocalDate.now()).build();
        Book newBook3 = Book.builder().title("BookCat").isbn("test3").publishDate(LocalDate.now()).build();

        when(bookRepository.findAll()).thenReturn(Arrays.asList(newBook1, newBook2, newBook3));

        assertThat(libraryService.getBooks()).hasSize(3);
    }
}
