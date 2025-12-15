package com.mycompany.library_manaement_system.Controller;

import com.mycompany.library_manaement_system.data.Models.User;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class UserController {

    private List<User> users;
    private final String filePath = "src/main/resources/users.txt";


    public UserController() {
        users = new ArrayList<>();
        readUsersFromFile();
    }

    private void readUsersFromFile() {
        users.clear();
        File file = new File(filePath);
        if (!file.exists()) {
            return; // File doesn't exist yet, nothing to read
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split("\\|");

                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String username = parts[1];
                    String password = parts[2];
                    User.UserType type = User.UserType.valueOf(parts[3]);
                    String gmail = parts[4];

                    // Fixed constructor argument order: id, username, password, gmail, type
                    User user = new User(id, username, password, gmail, type);
                    users.add(user);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }

    private int generateNewUserId() {
        int maxId = 0;
        for (User user : users) {
            if (user.getId() > maxId)
                maxId = user.getId();
        }
        return maxId + 1;
    }

    // ---------------- LOGIN ----------------

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // ---------------- CRUD ----------------

    public boolean addUser(User newUser) {

        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                return false;
            }
        }

        newUser.setId(generateNewUserId());
        users.add(newUser);
        saveUsersToFile();
        return true;
    }

    public boolean updateUser(int id, String newUsername, String newPassword, String newGmail, User.UserType newType) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                user.setGmail(newGmail);
                user.setType(newType);
                saveUsersToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int id) {
        boolean removed = users.removeIf(user -> user.getId() == id);

        if (removed)
            saveUsersToFile();
        return removed;
    }

    // ---------------- SEARCH ----------------

    public List<User> searchByUsername(String username) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(username.toLowerCase())) {
                result.add(user);
            }
        }
        return result;
    }

    public List<User> searchByType(User.UserType type) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getType() == type) {
                result.add(user);
            }
        }
        return result;
    }

    // ---------------- SAVE ----------------

    private void saveUsersToFile() {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs(); // Ensure directory exists
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (User user : users) {
                    String line = user.getId() + "|" + user.getUsername() + "|" +
                            user.getPassword() + "|" + user.getType() + "|" +
                            user.getGmail();
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing users file: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        return users;
    }
}
