package org.jakubmiczek.cli;

import org.jakubmiczek.exception.BookAlreadyExistException;
import org.jakubmiczek.exception.BookNotFoundException;
import org.jakubmiczek.model.Book;
import org.jakubmiczek.service.LibraryService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class LibraryConsoleApp {
    private final LibraryService service;
    private final Scanner scanner;

    public LibraryConsoleApp(LibraryService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- WELCOME TO THE LIBRARY CONSOLE APP ---");
            System.out.println("1. Add book");
            System.out.println("2. Show all books");
            System.out.println("3. Search book by title");
            System.out.println("4. Delete book");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");
            String choose = scanner.nextLine();

            switch (choose) {
                case "1" -> handleAddBook();
                case "2" -> handleShowAllBooks();
                case "3" -> handleSearchBookByTitle();
                case "4" -> handleDeleteBook();
                case "0" -> {
                    System.out.println("\nSee you soon!");
                    running = false;
                }
                default -> System.out.println("[SYSTEM]: Wrong choice, read carefully");
            }
        }
    }

    private void handleAddBook() {
        System.out.print("\nEnter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter publish date: ");

        String publishDate = scanner.nextLine();

        Book book;
        try {
            book = Book.builder().title(title).author(author).isbn(isbn).publishDate(LocalDate.parse(publishDate)).build();
        } catch (DateTimeParseException e) {
            System.out.println("[SYSTEM]: Invalid date format");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        try{
            if(book == null) return;
            service.addBook(book);
            System.out.println("[SYSTEM]: Book added successfully!");
        } catch (BookAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleShowAllBooks() {
        List<Book> allBooks = service.getBooks();

        System.out.println("\nAll Books Data:");
        printBook(allBooks);
    }

    private void handleSearchBookByTitle() {
        System.out.print("\nEnter title: ");
        String title = scanner.nextLine();

        List<Book> matchedBooks = service.search(title);

        System.out.println("\nMatched Books Data:");
        if(matchedBooks.isEmpty()) System.out.println("[SYSTEM]: No matched books found");
        else printBook(matchedBooks);
    }

    private void handleDeleteBook() {
        System.out.print("\nEnter ISBN: ");
        String isbn = scanner.nextLine();

        try{
            service.deleteBook(isbn);
            System.out.println("[SYSTEM]: Book deleted successfully!");
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printBook(List<Book> books) {
        for (Book book : books) {
            System.out.println("\nBook ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Publish Date: " + book.getPublishDate());
        }
    }
}
