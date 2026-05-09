package org.jakubmiczek.service;

import org.jakubmiczek.exception.BookAlreadyExistException;
import org.jakubmiczek.exception.BookNotFoundException;
import org.jakubmiczek.model.Book;
import org.jakubmiczek.repository.BookRepository;

import java.util.Comparator;
import java.util.List;

public class LibraryService {
    private final BookRepository bookRepository;

    public LibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) {
        if(bookRepository.findByIsbn(book.getIsbn()).isEmpty()) {
            bookRepository.save(book);
        } else throw new BookAlreadyExistException("[SYSTEM]: Book with ISBN: "+book.getIsbn()+ " already exists");
    }

    public List<Book> search(String fragment) {
        return bookRepository.findAll().stream().filter(b -> b.getTitle().toLowerCase().contains(fragment.toLowerCase())).toList();
    }

    public void deleteBook(String isbn) {
        if(bookRepository.findByIsbn(isbn).isPresent()) {
            bookRepository.deleteByIsbn(isbn);
        } else throw new BookNotFoundException("[SYSTEM]: Book with ISBN: "+isbn+ " not found");
    }

    public List<Book> getBooks() {
        return bookRepository.findAll().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }
}
