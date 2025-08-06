// FULL ADVANCED JAVA BANKING APP
// Author: Yuvaraj & ChatGPT

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FullAdvancedBankingApp {

    private static Map<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static final File logFile = new File("transaction_log.txt");

    public static void main(String[] args) {
        users.put("yuvaraj", new User("yuvaraj", "summa"));
        showLoginScreen();
    }

    static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(null);

        JLabel userLabel = new JLabel("Username : ");
        userLabel.setBounds(20, 20, 80, 25);
        loginFrame.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(100, 20, 150, 25);
        loginFrame.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 25);
        loginFrame.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 150, 25);
        loginFrame.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 100, 80, 25);
        loginFrame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (users.containsKey(username) && users.get(username).password.equals(password)) {
                currentUser = users.get(username);
                loginFrame.dispose();
                showBankingUI();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials");
            }
        });

        loginFrame.setVisible(true);
    }

    static void showBankingUI() {
        JFrame frame = new JFrame("Advanced Banking - User: " + currentUser.username);
        frame.setSize(900, 700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel balanceLabel = new JLabel("Balance: $" + format(currentUser.balance));
        balanceLabel.setBounds(20, 10, 300, 25);
        frame.add(balanceLabel);

        JTextField depositField = new JTextField();
        depositField.setBounds(160, 40, 100, 25);
        frame.add(depositField);
        String[] categories = {"Salary", "Gift", "Refund"};
        JComboBox<String> depositCategory = new JComboBox<>(categories);
        depositCategory.setBounds(270, 40, 100, 25);
        frame.add(depositCategory);

        JTextField withdrawField = new JTextField();
        withdrawField.setBounds(160, 80, 100, 25);
        frame.add(withdrawField);
        String[] withdrawCategories = {"Shopping", "Bills", "Rent"};
        JComboBox<String> withdrawCategory = new JComboBox<>(withdrawCategories);
        withdrawCategory.setBounds(270, 80, 100, 25);
        frame.add(withdrawCategory);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(380, 40, 100, 25);
        frame.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(380, 80, 100, 25);
        frame.add(withdrawBtn);

        JButton exportPdf = new JButton("Export PDF (Mock)");
        exportPdf.setBounds(500, 40, 150, 25);
        frame.add(exportPdf);

        JButton clearLog = new JButton("Clear Log");
        clearLog.setBounds(500, 80, 150, 25);
        frame.add(clearLog);

        JTextArea logArea = new JTextArea("Transaction Log:\n");
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBounds(20, 120, 840, 200);
        frame.add(scrollPane);
        logArea.setEditable(false);

        JTabbedPane loanTabs = new JTabbedPane();
        loanTabs.setBounds(20, 340, 840, 300);
        String[] loanTypes = {"Personal", "Home", "Car"};
        for (String type : loanTypes) {
            loanTabs.add(type, createLoanTab(type, balanceLabel, logArea));
        }
        frame.add(loanTabs);

        depositBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(depositField.getText());
                if (amount <= 0) throw new NumberFormatException();
                currentUser.balance += amount;
                balanceLabel.setText("Balance: $" + format(currentUser.balance));
                log(logArea, "Deposited $" + format(amount) + " [" + depositCategory.getSelectedItem() + "]");
            } catch (Exception ex) {
                log(logArea, "Invalid deposit amount");
            }
            depositField.setText("");
        });

        withdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(withdrawField.getText());
                if (amount <= 0 || amount > currentUser.balance) throw new NumberFormatException();
                currentUser.balance -= amount;
                balanceLabel.setText("Balance: $" + format(currentUser.balance));
                log(logArea, "Withdrawn $" + format(amount) + " [" + withdrawCategory.getSelectedItem() + "]");
            } catch (Exception ex) {
                log(logArea, "Invalid or insufficient balance");
            }
            withdrawField.setText("");
        });

        exportPdf.addActionListener(e -> log(logArea, "PDF Exported Successfully (Mock)"));

        clearLog.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Clear all logs?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try { new FileWriter(logFile, false).close(); } catch (Exception ignored) {}
                logArea.setText("Transaction Log:\n");
                log(logArea, "Log cleared by user.");
            }
        });

        frame.setVisible(true);
    }

    static JPanel createLoanTab(String type, JLabel balanceLabel, JTextArea logArea) {
        JPanel panel = new JPanel(null);
        JTextField amountField = new JTextField();
        amountField.setBounds(10, 10, 100, 25);
        panel.add(amountField);

        JComboBox<Integer> tenureBox = new JComboBox<>(new Integer[]{6, 12, 24, 36});
        tenureBox.setBounds(120, 10, 80, 25);
        panel.add(tenureBox);

        JButton requestBtn = new JButton("Request Loan");
        requestBtn.setBounds(210, 10, 130, 25);
        panel.add(requestBtn);

        JLabel emiLabel = new JLabel("EMI: $0.00 | Remaining: 0");
        emiLabel.setBounds(10, 50, 300, 25);
        panel.add(emiLabel);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(10, 80, 300, 20);
        panel.add(progressBar);

        JButton payBtn = new JButton("Pay EMI");
        payBtn.setBounds(10, 110, 100, 25);
        payBtn.setEnabled(false);
        panel.add(payBtn);

        requestBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                int months = (int) tenureBox.getSelectedItem();
                double interest = amount * 0.08 * (months / 12.0);
                double emi = (amount + interest) / months;

                Loan loan = new Loan(amount + interest, emi, months);
                currentUser.loans.put(type, loan);

                emiLabel.setText("EMI: $" + format(emi) + " | Remaining: " + months);
                payBtn.setEnabled(true);
                progressBar.setMaximum(months);
                progressBar.setValue(0);
                log(logArea, type + " Loan Approved: $" + format(amount) + ", Tenure: " + months + " months");
            } catch (Exception ex) {
                log(logArea, "Invalid loan input");
            }
        });

        payBtn.addActionListener(e -> {
            Loan loan = currentUser.loans.get(type);
            if (loan != null && loan.monthsRemaining > 0 && currentUser.balance >= loan.emi) {
                currentUser.balance -= loan.emi;
                loan.monthsRemaining--;
                emiLabel.setText("EMI: $" + format(loan.emi) + " | Remaining: " + loan.monthsRemaining);
                balanceLabel.setText("Balance: $" + format(currentUser.balance));
                progressBar.setValue(progressBar.getMaximum() - loan.monthsRemaining);
                log(logArea, "EMI Paid for " + type + ": $" + format(loan.emi));

                if (loan.monthsRemaining == 0) {
                    payBtn.setEnabled(false);
                    log(logArea, type + " Loan Fully Paid! 🎉");
                }
            } else {
                log(logArea, "Cannot pay EMI - check balance or loan status");
            }
        });

        return panel;
    }

    static void log(JTextArea area, String text) {
        String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entry = "[" + stamp + "] " + text;
        area.append(entry + "\n");
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(entry + "\n");
        } catch (Exception ignored) {}
        area.setCaretPosition(area.getDocument().getLength());
    }

    static String format(double n) {
        return String.format("%.2f", n);
    }

    static class User {
        String username, password;
        double balance = 1000.0;
        Map<String, Loan> loans = new HashMap<>();

        User(String u, String p) {
            username = u;
            password = p;
        }
    }

    static class Loan {
        double amount, emi;
        int monthsRemaining;

        Loan(double amt, double emi, int months) {
            this.amount = amt;
            this.emi = emi;
            this.monthsRemaining = months;
        }
    }
} 
