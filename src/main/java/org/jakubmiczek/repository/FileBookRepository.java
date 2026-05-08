package org.jakubmiczek.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jakubmiczek.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FileBookRepository implements BookRepository {
    private final File file;
    private final ObjectMapper mapper;

    public FileBookRepository(String fileName) {
        this.file = new File(fileName);
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void save(Book book) {
        List<Book> books = findAll();
        books.removeIf(b -> b.getIsbn().equals(book.getIsbn()));
        books.add(book);
        writeAll(books);
    }

    @Override
    public List<Book> findAll() {
        if (!file.exists()) return new ArrayList<>();
        try {
            return mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read library file: " + file.getAbsolutePath());
        }
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return findAll().stream().filter(b -> b.getIsbn().equals(isbn)).findFirst();
    }

    @Override
    public void deleteByIsbn(String isbn) {
        List<Book> books = findAll();
        books.removeIf(b -> b.getIsbn().equals(isbn));
        writeAll(books);
    }

    private void writeAll(List<Book> books) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, books);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save library file: " + file.getAbsolutePath());
        }
    }
}
