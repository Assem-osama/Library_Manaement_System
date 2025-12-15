package com.mycompany.library_manaement_system.data.Models;

public class User {

    public enum UserType {
        Admin,
        Librarian,
        Patron
    }

    private int id;
    private String username;
    private String password;
    private String gmail;
    private UserType type;

    public User(int id, String username, String password, String gmail, UserType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gmail = gmail;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
