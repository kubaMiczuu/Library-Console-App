package org.jakubmiczek;

import org.jakubmiczek.cli.LibraryConsoleApp;
import org.jakubmiczek.repository.BookRepository;
import org.jakubmiczek.repository.FileBookRepository;
import org.jakubmiczek.service.LibraryService;

public class LibraryApp {
    static void main() {
        BookRepository bookRepository = new FileBookRepository("library.json");
        LibraryService libraryService = new LibraryService(bookRepository);

        new LibraryConsoleApp(libraryService).start();
    }
}
