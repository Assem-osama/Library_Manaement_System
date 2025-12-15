package com.mycompany.library_manaement_system.data.Models;

import java.time.LocalDate;

public class Transaction {

    public enum TransactionType {
        CheckOut,
        Return,
        Reserve,
        Renew
    }

    private int id;
    private int userId;
    private int bookId;
    private TransactionType type;
    private LocalDate date;

    public Transaction(int id, int userId, int bookId, TransactionType type, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.type = type;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
