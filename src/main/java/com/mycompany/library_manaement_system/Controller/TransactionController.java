package com.mycompany.library_manaement_system.Controller;

import com.mycompany.library_manaement_system.data.Models.Book;
import com.mycompany.library_manaement_system.data.Models.Transaction;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionController {

    private List<Transaction> transactions;
    private final String filePath = "src/main/resources/transactions.txt";

    private BookController bookController;

    public TransactionController(BookController bookController) {
        this.bookController = bookController;
        transactions = new ArrayList<>();
        readTransactionsFromFile();
    }

    public void readTransactionsFromFile() {
        transactions.clear();
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length == 5) {
                        int id = Integer.parseInt(parts[0].trim());
                        int userId = Integer.parseInt(parts[1].trim());
                        int bookId = Integer.parseInt(parts[2].trim());

                        String typeStr = parts[3].trim().toLowerCase();
                        Transaction.TransactionType type = null;

                        if (typeStr.equals("check-out")) {
                            type = Transaction.TransactionType.CheckOut;
                        } else if (typeStr.equals("available")) {
                            type = Transaction.TransactionType.Return;
                        } else {
                            for (Transaction.TransactionType t : Transaction.TransactionType.values()) {
                                if (t.name().equalsIgnoreCase(typeStr)) {
                                    type = t;
                                    break;
                                }
                            }
                        }

                        if (type == null) {
                            System.out.println("Unknown transaction type: " + typeStr);
                            continue; // Skip invalid types
                        }

                        LocalDate date = LocalDate.parse(parts[4].trim(), formatter);
                        transactions.add(new Transaction(id, userId, bookId, type, date));
                    }
                } catch (Exception e) {
                    System.out.println("Skipping malformed line in transactions file: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions file: " + e.getMessage());
        }
    }

    private void saveTransactionsToFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Transaction t : transactions) {
                    writer.write(t.getId() + "|" + t.getUserId() + "|" + t.getBookId() + "|" +
                            t.getType() + "|" + t.getDate().format(formatter));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing transactions file: " + e.getMessage());
        }
    }

    private int generateNewId() {
        int maxId = 0;
        for (Transaction t : transactions) {
            if (t.getId() > maxId)
                maxId = t.getId();
        }
        return maxId + 1;
    }

    private void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateBookStatus(transaction.getBookId(), transaction.getType());
        saveTransactionsToFile();
    }

    public boolean checkoutBook(int userId, int bookId) {
        Book.BookStatus status = bookController.getBookStatus(bookId);
        if (status != Book.BookStatus.Available)
            return false;

        int newId = generateNewId();
        Transaction transaction = new Transaction(newId, userId, bookId, Transaction.TransactionType.CheckOut,
                LocalDate.now());
        addTransaction(transaction);
        return true;
    }

    public boolean returnBook(int userId, int bookId) {
        Book.BookStatus status = bookController.getBookStatus(bookId);
        if (status != Book.BookStatus.CheckedOut)
            return false;

        int newId = generateNewId();
        Transaction transaction = new Transaction(newId, userId, bookId, Transaction.TransactionType.Return,
                LocalDate.now());
        addTransaction(transaction);
        return true;
    }

    public boolean reserveBook(int userId, int bookId) {
        Book.BookStatus status = bookController.getBookStatus(bookId);
        if (status != Book.BookStatus.Available)
            return false;

        int newId = generateNewId();
        Transaction transaction = new Transaction(newId, userId, bookId, Transaction.TransactionType.Reserve,
                LocalDate.now());
        addTransaction(transaction);
        return true;
    }

    public boolean renewBook(int userId, int bookId) {
        Book.BookStatus status = bookController.getBookStatus(bookId);
        if (status != Book.BookStatus.CheckedOut)
            return false;

        int newId = generateNewId();
        Transaction transaction = new Transaction(newId, userId, bookId,
                Transaction.TransactionType.Renew, LocalDate.now());

        transactions.add(transaction);
        saveTransactionsToFile();
        return true;
    }

    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> history = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getUserId() == userId)
                history.add(t);
        }
        return history;
    }

    private void updateBookStatus(int bookId, Transaction.TransactionType type) {
        switch (type) {
            case CheckOut -> bookController.updateBookStatus(bookId, Book.BookStatus.CheckedOut);
            case Return -> bookController.updateBookStatus(bookId, Book.BookStatus.Available);
            case Reserve -> bookController.updateBookStatus(bookId, Book.BookStatus.Reserved);
            case Renew -> bookController.updateBookStatus(bookId, Book.BookStatus.CheckedOut);
        }
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }
}
