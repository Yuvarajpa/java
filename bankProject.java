import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class bankProject {

    private static double balance = 1000.0;
    private static final File logFile = new File("transaction_log.txt");
    private static final Map<String, Loan> loans = new HashMap<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Multi-Loan Banking Application");
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel statusLabel = new JLabel("Account Status: Savings Balance: $" + format(balance));
        statusLabel.setBounds(20, 10, 500, 20);
        frame.add(statusLabel);

        JTextField depositField = new JTextField();
        depositField.setBounds(160, 40, 150, 25);
        frame.add(depositField);

        JTextField withdrawField = new JTextField();
        withdrawField.setBounds(160, 80, 150, 25);
        frame.add(withdrawField);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(330, 40, 100, 25);
        frame.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(330, 80, 100, 25);
        frame.add(withdrawBtn);

        JButton viewStatementBtn = new JButton("View Statement");
        viewStatementBtn.setBounds(20, 120, 150, 25);
        frame.add(viewStatementBtn);

        JButton printStatementBtn = new JButton("Print Statement");
        printStatementBtn.setBounds(180, 120, 150, 25);
        frame.add(printStatementBtn);

        JButton clearLogBtn = new JButton("Clear Log");
        clearLogBtn.setBounds(340, 120, 120, 25);
        frame.add(clearLogBtn);

        JTextArea logArea = new JTextArea("Transaction Log:\n");
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBounds(20, 160, 740, 250);
        logArea.setEditable(false);
        frame.add(scrollPane);

        JTabbedPane loanTabs = new JTabbedPane();
        loanTabs.setBounds(20, 420, 740, 200);
        String[] loanTypes = {"Personal", "Home", "Car"};

        for (String type : loanTypes) {
            loanTabs.add(type + " Loan", createLoanPanel(type, logArea, statusLabel));
        }

        frame.add(loanTabs);

        depositBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(depositField.getText());
                if (amount <= 0) {
                    appendLog(logArea, "Deposit amount must be greater than zero!");
                    depositField.setText("");
                    return;
                }
                balance += amount;
                appendLog(logArea, "Deposited: $" + format(amount));
                statusLabel.setText("Account Status: Savings Balance: $" + format(balance));
                writeToLog("Deposited: $" + format(amount));
            } catch (NumberFormatException ex) {
                appendLog(logArea, "Invalid input for deposit!");
            }
            depositField.setText("");
        });

        withdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(withdrawField.getText());
                if (amount <= 0) {
                    appendLog(logArea, "Withdrawal amount must be greater than zero!");
                    withdrawField.setText("");
                    return;
                }
                if (amount <= balance) {
                    balance -= amount;
                    appendLog(logArea, "Withdrawn: $" + format(amount));
                    statusLabel.setText("Account Status: Savings Balance: $" + format(balance));
                    writeToLog("Withdrawn: $" + format(amount));
                } else {
                    appendLog(logArea, "Insufficient balance!");
                }
            } catch (NumberFormatException ex) {
                appendLog(logArea, "Invalid input for withdrawal!");
            }
            withdrawField.setText("");
        });

        viewStatementBtn.addActionListener(e -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                logArea.setText("Transaction Log:\n");
                String line;
                while ((line = reader.readLine()) != null) {
                    logArea.append(line + "\n");
                }
                logArea.setCaretPosition(logArea.getDocument().getLength());
            } catch (IOException ex) {
                appendLog(logArea, "Unable to load statement.");
            }
        });

        printStatementBtn.addActionListener(e -> {
            try {
                boolean success = logArea.print();
                appendLog(logArea, success ? "Statement Printed Successfully." : "Print Cancelled.");
            } catch (Exception ex) {
                appendLog(logArea, "Print Error: " + ex.getMessage());
            }
        });

        clearLogBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all logs?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    new FileWriter(logFile, false).close();
                    logArea.setText("Transaction Log:\n");
                    appendLog(logArea, "Log cleared by user.");
                } catch (IOException ex) {
                    appendLog(logArea, "Failed to clear log.");
                }
            }
        });

        frame.setVisible(true);
    }

    private static JPanel createLoanPanel(String loanType, JTextArea logArea, JLabel statusLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel amountLabel = new JLabel("Loan Amount:");
        amountLabel.setBounds(10, 10, 100, 25);
        panel.add(amountLabel);

        JTextField loanField = new JTextField();
        loanField.setBounds(120, 10, 150, 25);
        panel.add(loanField);

        JButton requestBtn = new JButton("Request Loan");
        requestBtn.setBounds(290, 10, 130, 25);
        panel.add(requestBtn);

        JLabel emiLabel = new JLabel("EMI: $0.00 | Remaining: 0");
        emiLabel.setBounds(10, 50, 250, 25);
        panel.add(emiLabel);

        JButton payEmiBtn = new JButton("Pay EMI");
        payEmiBtn.setBounds(120, 90, 120, 25);
        payEmiBtn.setEnabled(false);
        panel.add(payEmiBtn);

        requestBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(loanField.getText());
                if (amount <= 0) {
                    appendLog(logArea, loanType + " loan amount must be greater than zero!");
                    loanField.setText("");
                    return;
                }

                Loan existingLoan = loans.get(loanType);
                if (existingLoan != null && existingLoan.monthsRemaining > 0) {
                    appendLog(logArea, loanType + " loan is already active! Please repay it first.");
                    loanField.setText("");
                    return;
                }

                Loan loan = new Loan(amount);
                loans.put(loanType, loan);
                emiLabel.setText(String.format("EMI: $%s | Remaining: %d", format(loan.emi), loan.monthsRemaining));
                appendLog(logArea, loanType + " Loan Approved: $" + format(amount));
                writeToLog(loanType + " Loan Approved: $" + format(amount));
                payEmiBtn.setEnabled(true);
            } catch (NumberFormatException ex) {
                appendLog(logArea, "Invalid input for " + loanType + " loan!");
            }
            loanField.setText("");
        });

        payEmiBtn.addActionListener(e -> {
            Loan loan = loans.get(loanType);
            if (loan != null && loan.monthsRemaining > 0) {
                if (balance >= loan.emi) {
                    balance -= loan.emi;
                    loan.amount -= loan.emi;
                    loan.monthsRemaining--;

                    emiLabel.setText(String.format("EMI: $%s | Remaining: %d", format(loan.emi), loan.monthsRemaining));
                    statusLabel.setText("Account Status: Savings Balance: $" + format(balance));
                    appendLog(logArea, "EMI Paid for " + loanType + ": $" + format(loan.emi));
                    writeToLog("EMI Paid for " + loanType + ": $" + format(loan.emi));

                    if (loan.monthsRemaining == 0) {
                        appendLog(logArea, loanType + " Loan Fully Paid!");
                        writeToLog(loanType + " Loan Fully Paid");
                        payEmiBtn.setEnabled(false);
                    }
                } else {
                    appendLog(logArea, "Not enough balance to pay EMI for " + loanType);
                }
            }
        });

        return panel;
    }

    private static void appendLog(JTextArea area, String text) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        area.append("[" + timestamp + "] " + text + "\n");
        area.setCaretPosition(area.getDocument().getLength());
    }

    private static void writeToLog(String entry) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write("[" + timestamp + "] " + entry + "\n");
        } catch (IOException ex) {
            System.err.println("Log write error: " + ex.getMessage());
        }
    }

    private static String format(double amount) {
        return String.format("%.2f", amount);
    }

    static class Loan {
        double amount;
        double emi;
        int monthsRemaining;

        Loan(double amount) {
            this.amount = amount;
            this.emi = amount / 12;
            this.monthsRemaining = 12;
        }
    }
}
