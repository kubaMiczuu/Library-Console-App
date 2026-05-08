package org.jakubmiczek.repository;

import org.jakubmiczek.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    List<Book> findAll();
    Optional<Book> findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
}
