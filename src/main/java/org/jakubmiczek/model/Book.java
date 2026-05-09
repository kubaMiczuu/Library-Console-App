package org.jakubmiczek.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class Book {
    final UUID id;
    final String title;
    final String author;
    final String isbn;
    final LocalDate publishDate;

    public static class BookBuilder{
        public Book build() {
            if(title == null || title.isBlank()) {
                throw new IllegalArgumentException("[SYSTEM]: Title cannot be empty");
            }
            if(isbn == null || isbn.isBlank()) {
                throw new IllegalArgumentException("[SYSTEM]: ISBN cannot be empty");
            }
            if(publishDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("[SYSTEM]: Publish date cannot be after now");
            }

            UUID finalID = id == null ? UUID.randomUUID() : id;

            return new Book(finalID, title, author, isbn, publishDate);
        }
    }
}
