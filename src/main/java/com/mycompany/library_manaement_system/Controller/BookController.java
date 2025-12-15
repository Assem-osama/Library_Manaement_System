package com.mycompany.library_manaement_system.Controller;

import com.mycompany.library_manaement_system.data.Models.Book;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class BookController {

    private List<Book> books;
    private final String filePath = "src/main/resources/books.txt";

    public BookController() {
        books = new ArrayList<>();
        readBooksFromFile();
    }

    public void readBooksFromFile() {
        books.clear();
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length == 6) {
                        int id = Integer.parseInt(parts[0].trim());
                        String title = parts[1].trim();
                        String author = parts[2].trim();
                        String genre = parts[3].trim();
                        int year = Integer.parseInt(parts[4].trim());

                        String statusStr = parts[5].trim().toLowerCase();
                        Book.BookStatus status;

                        if (statusStr.equals("checked out")) {
                            status = Book.BookStatus.CheckedOut;
                        } else {
                            // Capitalize first letter for standard Enum.valueOf if it matches standard
                            // names
                            // Or just iterate to find match
                            status = null;
                            for (Book.BookStatus s : Book.BookStatus.values()) {
                                if (s.name().equalsIgnoreCase(statusStr)) {
                                    status = s;
                                    break;
                                }
                            }
                            // Default to Available if not found or handle error?
                            // Let's assume Available if null to avoid crash, or throw exception
                            if (status == null) {
                                System.out.println("Unknown status: " + statusStr + ", defaulting to Available");
                                status = Book.BookStatus.Available;
                            }
                        }

                        Book book = new Book(id, title, author, genre, year, status);
                        books.add(book);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping malformed line in books file: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books file: " + e.getMessage());
        }
    }

    private int generateNewBookId() {
        int maxId = 0;
        for (Book b : books) {
            if (b.getId() > maxId)
                maxId = b.getId();
        }
        return maxId + 1;
    }

    public boolean addBook(Book newBook) {
        newBook.setId(generateNewBookId());
        books.add(newBook);
        saveBooksToFile();
        return true;
    }

    public boolean updateBook(int id, String newTitle, String newAuthor, String newGenre, int newYear,
            Book.BookStatus newStatus) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setGenre(newGenre);
                book.setYear(newYear);
                book.setStatus(newStatus);
                saveBooksToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteBook(int id) {
        boolean removed = books.removeIf(book -> book.getId() == id);
        if (removed)
            saveBooksToFile();
        return removed;
    }

    // ---- SEARCH ----

    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> searchByGenre(String genre) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> searchByYear(int year) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getYear() == year) {
                result.add(b);
            }
        }
        return result;
    }

    // ---- STATUS ----

    public boolean updateBookStatus(int id, Book.BookStatus newStatus) {
        for (Book b : books) {
            if (b.getId() == id) {
                b.setStatus(newStatus);
                saveBooksToFile();
                return true;
            }
        }
        return false;
    }

    public Book.BookStatus getBookStatus(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return b.getStatus();
            }
        }
        return null;
    }

    // ---- SAVE ----
    private void saveBooksToFile() {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Book book : books) {
                    String line = book.getId() + "|" + book.getTitle() + "|" + book.getAuthor() + "|" +
                            book.getGenre() + "|" + book.getYear() + "|" + book.getStatus();
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing books file: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        return books;
    }
}
