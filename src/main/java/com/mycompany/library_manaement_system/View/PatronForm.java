package com.mycompany.library_manaement_system.View;

import com.mycompany.library_manaement_system.Controller.BookController;
import com.mycompany.library_manaement_system.Controller.TransactionController;
import com.mycompany.library_manaement_system.Controller.UserController;
import com.mycompany.library_manaement_system.data.Models.Book;
import com.mycompany.library_manaement_system.data.Models.Transaction;
import com.mycompany.library_manaement_system.data.Models.User;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class PatronForm extends javax.swing.JFrame {

    private User user;
    private BookController bookController;
    private TransactionController transactionController;
    private UserController userController;
    private DefaultTableModel searchTableModel;
    private DefaultTableModel historyTableModel;

    public PatronForm(User user) {
        this.user = user;
        bookController = new BookController();
        transactionController = new TransactionController(bookController);
        userController = new UserController();

        initComponents();
        initCustomComponents();
        setTitle("Patron Dashboard - " + user.getUsername());
        setLocationRelativeTo(null);
    }

    public PatronForm() {
        initComponents();
    }

    private void initCustomComponents() {
        // Search Table
        String[] bookColumns = { "ID", "Title", "Author", "Genre", "Year", "Status" };
        searchTableModel = new DefaultTableModel(bookColumns, 0);
        searchTable.setModel(searchTableModel);

        // History Table
        String[] transColumns = { "Book Title", "Type", "Date" };
        historyTableModel = new DefaultTableModel(transColumns, 0);
        historyTable.setModel(historyTableModel);

        loadAllBooks();
        loadAllBooks();
        loadHistory();

        addProfileTab();
    }

    private void addProfileTab() {
        javax.swing.JPanel profilePanel = new javax.swing.JPanel();
        javax.swing.JLabel lblUsername = new javax.swing.JLabel("Username:");
        javax.swing.JLabel lblEmail = new javax.swing.JLabel("Email:");
        javax.swing.JLabel lblPassword = new javax.swing.JLabel("Password:");

        javax.swing.JTextField txtUsername = new javax.swing.JTextField(user.getUsername());
        txtUsername.setEditable(false);

        javax.swing.JTextField txtEmail = new javax.swing.JTextField(user.getGmail());
        javax.swing.JPasswordField txtPassword = new javax.swing.JPasswordField(user.getPassword());
        javax.swing.JButton btnSave = new javax.swing.JButton("Save Changes");

        btnSave.addActionListener(e -> {
            String newEmail = txtEmail.getText();
            String newPass = new String(txtPassword.getPassword());

            if (newEmail.isEmpty() || newPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email and Password cannot be empty.");
                return;
            }

            if (userController.updateUser(user.getId(), user.getUsername(), newPass, newEmail, user.getType())) {
                JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
                user.setGmail(newEmail);
                user.setPassword(newPass);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile.");
            }
        });

        javax.swing.GroupLayout profileLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profileLayout);
        profileLayout.setHorizontalGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profileLayout.createSequentialGroup()
                        .addGap(20)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblUsername)
                                .addComponent(lblEmail)
                                .addComponent(lblPassword))
                        .addGap(20)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(txtEmail)
                                .addComponent(txtPassword)
                                .addComponent(btnSave))
                        .addContainerGap(200, Short.MAX_VALUE)));

        profileLayout.setVerticalGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profileLayout.createSequentialGroup()
                        .addGap(20)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblUsername)
                                .addComponent(txtUsername))
                        .addGap(15)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEmail)
                                .addComponent(txtEmail))
                        .addGap(15)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPassword)
                                .addComponent(txtPassword))
                        .addGap(25)
                        .addComponent(btnSave)
                        .addContainerGap(200, Short.MAX_VALUE)));

        jTabbedPane1.addTab("My Profile", profilePanel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        logoutButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        searchPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        borrowButton = new javax.swing.JButton();
        reserveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        accountPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        refreshHistoryButton = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();
        renewButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Search (Title/Author/Genre):");

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        borrowButton.setText("Check out Selected");
        borrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowButtonActionPerformed(evt);
            }
        });

        reserveButton.setText("Reserve Selected");
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(searchButton)
                                .addGap(18, 18, 18)
                                .addComponent(borrowButton)
                                .addGap(18, 18, 18)
                                .addComponent(reserveButton)
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchButton)
                                        .addComponent(borrowButton)
                                        .addComponent(reserveButton))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        searchTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jScrollPane1.setViewportView(searchTable);

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
                searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(searchPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1))
                                .addContainerGap()));
        searchPanelLayout.setVerticalGroup(
                searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(searchPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                                .addContainerGap()));

        jTabbedPane1.addTab("Search & Check out", searchPanel);

        historyTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jScrollPane2.setViewportView(historyTable);

        refreshHistoryButton.setText("Refresh History");
        refreshHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshHistoryButtonActionPerformed(evt);
            }
        });

        returnButton.setText("Return Book");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        renewButton.setText("Renew Book");
        renewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renewButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout accountPanelLayout = new javax.swing.GroupLayout(accountPanel);
        accountPanel.setLayout(accountPanelLayout);
        accountPanelLayout.setHorizontalGroup(
                accountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(accountPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(accountPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 988,
                                                Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                accountPanelLayout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(renewButton)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(returnButton)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(refreshHistoryButton)))
                                .addContainerGap()));
        accountPanelLayout.setVerticalGroup(
                accountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(accountPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 465,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20,
                                        Short.MAX_VALUE)
                                .addGroup(accountPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(refreshHistoryButton)
                                        .addComponent(returnButton)
                                        .addComponent(renewButton))
                                .addContainerGap()));

        jTabbedPane1.addTab("History", accountPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(logoutButton)
                                .addContainerGap())
                        .addComponent(jTabbedPane1));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logoutButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTabbedPane1)
                                .addContainerGap()));

        pack();
    }// </editor-fold>

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new RegisterForm().setVisible(true);
        dispose();
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String query = searchField.getText().toLowerCase();
        List<Book> allBooks = bookController.getAllBooks();
        List<Book> filtered = allBooks.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query) ||
                        b.getAuthor().toLowerCase().contains(query) ||
                        b.getGenre().toLowerCase().contains(query))
                .collect(Collectors.toList());
        displayBooks(filtered);
    }

    private void borrowButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = searchTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to Check out.");
            return;
        }
        int bookId = (int) searchTableModel.getValueAt(row, 0);

        if (transactionController.checkoutBook(user.getId(), bookId)) {
            JOptionPane.showMessageDialog(this, "Book Check out Successfully!");
            loadAllBooks(); // Refresh table status
            loadHistory();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot Check out book. It might not be available.");
        }
    }

    private void reserveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = searchTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to reserve.");
            return;
        }
        int bookId = (int) searchTableModel.getValueAt(row, 0);

        if (transactionController.reserveBook(user.getId(), bookId)) {
            JOptionPane.showMessageDialog(this, "Book Reserved Successfully!");
            loadAllBooks();
            loadHistory();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot reserve book. It might be already available or reserved.");
        }
    }

    private void refreshHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loadHistory();
    }

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = historyTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book from history to return.");
            return;
        }

        try {

            List<Transaction> userTrans = transactionController.getTransactionsByUserId(user.getId());
            if (row >= 0 && row < userTrans.size()) {
                Transaction t = userTrans.get(row);

                if (t.getType() != Transaction.TransactionType.CheckOut
                        && t.getType() != Transaction.TransactionType.Renew) {
                    JOptionPane.showMessageDialog(this,
                            "You can only return currently borrowed books (CheckOut/Renew status).");

                    return;
                }

                if (transactionController.returnBook(user.getId(), t.getBookId())) {
                    JOptionPane.showMessageDialog(this, "Book Returned Successfully!");
                    loadHistory();
                    loadAllBooks();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to return book. Maybe it's already returned?");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error returning book: " + e.getMessage());
        }
    }

    private void renewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = historyTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to renew.");
            return;
        }

        List<Transaction> userTrans = transactionController.getTransactionsByUserId(user.getId());
        if (row >= 0 && row < userTrans.size()) {
            Transaction t = userTrans.get(row);

            if (t.getType() != Transaction.TransactionType.CheckOut
                    && t.getType() != Transaction.TransactionType.Renew) {
                JOptionPane.showMessageDialog(this, "You can only renew currently borrowed books.");
                return;
            }

            if (transactionController.renewBook(user.getId(), t.getBookId())) {
                JOptionPane.showMessageDialog(this, "Book Renewed Successfully!");
                loadHistory();
                loadAllBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to renew book.");
            }
        }
    }

    private void loadAllBooks() {
        displayBooks(bookController.getAllBooks());
    }

    private void displayBooks(List<Book> books) {
        searchTableModel.setRowCount(0);
        for (Book b : books) {
            searchTableModel.addRow(
                    new Object[] { b.getId(), b.getTitle(), b.getAuthor(), b.getGenre(), b.getYear(), b.getStatus() });
        }
    }

    private void loadHistory() {
        historyTableModel.setRowCount(0);
        List<Transaction> myTrans = transactionController.getTransactionsByUserId(user.getId());
        for (Transaction t : myTrans) {
            Book b = bookController.getAllBooks().stream().filter(bk -> bk.getId() == t.getBookId()).findFirst()
                    .orElse(null);
            String title = (b != null) ? b.getTitle() : "Unknown (" + t.getBookId() + ")";
            historyTableModel.addRow(new Object[] { title, t.getType(), t.getDate() });
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JPanel accountPanel;
    private javax.swing.JButton borrowButton;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton refreshHistoryButton;
    private javax.swing.JButton reserveButton;
    private javax.swing.JButton returnButton;
    private javax.swing.JButton renewButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTable searchTable;
    // End of variables declaration
}
