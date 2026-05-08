package org.jakubmiczek.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@ToString
public class Book {
    final UUID id;
    final String title;
    final String author;
    final String isbn;
    final LocalDate publishDate;

    public static class BookBuilder{
        public Book build() {
            if(title == null || title.isBlank()) {
                throw new IllegalArgumentException("Title cannot be empty");
            }
            if(isbn == null || isbn.isBlank()) {
                throw new IllegalArgumentException("ISBN cannot be empty");
            }
            UUID finalID = id == null ? UUID.randomUUID() : id;

            return new Book(finalID, title, author, isbn, publishDate);
        }
    }
}
