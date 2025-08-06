import javax.swing.*;

public class BankingApp {

    private static double balance = 1000.0;
    private static double loanAmount = 0.0;
    private static double emi = 0.0;
    private static int monthsRemaining = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Banking Application");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel statusLabel = new JLabel("Account Status: Savings Balance: $1000.00");
        statusLabel.setBounds(20, 10, 500, 20);
        frame.add(statusLabel);

        JLabel loanStatus = new JLabel("Loan Status: Loan Amount: $0.00");
        loanStatus.setBounds(20, 30, 400, 20);
        frame.add(loanStatus);

        JLabel emiStatus = new JLabel("EMI Status: EMI: $0.00 (Remaining: 0 months)");
        emiStatus.setBounds(20, 50, 400, 20);
        frame.add(emiStatus);

        JTextField depositField = new JTextField();
        depositField.setBounds(160, 90, 150, 25);
        frame.add(depositField);

        JTextField withdrawField = new JTextField();
        withdrawField.setBounds(160, 130, 150, 25);
        frame.add(withdrawField);

        JTextField loanField = new JTextField();
        loanField.setBounds(160, 170, 150, 25);
        frame.add(loanField);

        JTextField payEmiField = new JTextField();
        payEmiField.setBounds(160, 210, 150, 25);
        payEmiField.setEnabled(false);
        frame.add(payEmiField);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(330, 90, 100, 25);
        frame.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(330, 130, 100, 25);
        frame.add(withdrawBtn);

        JButton loanBtn = new JButton("Request Loan");
        loanBtn.setBounds(330, 170, 150, 25);
        frame.add(loanBtn);

        JButton payEmiBtn = new JButton("Pay EMI");
        payEmiBtn.setBounds(330, 210, 100, 25);
        payEmiBtn.setEnabled(false);
        frame.add(payEmiBtn);

        JTextArea logArea = new JTextArea("Transaction Log:\n");
        logArea.setBounds(20, 250, 550, 1000);
        logArea.setEditable(false);
        frame.add(logArea);

        depositBtn.addActionListener(e -> {
            double amount = Double.parseDouble(depositField.getText());
            balance += amount;
            logArea.append("Deposited: $" + amount + "\n");
            statusLabel.setText("Account Status: Savings Balance: $" + balance);
                depositField.setText(""); // in this this i use to clear the loan filed

        });

        withdrawBtn.addActionListener(e -> {
            double amount = Double.parseDouble(withdrawField.getText());
            if (amount <= balance) {
                balance -= amount;
                logArea.append("Withdrawn: $" + amount + "\n");
                statusLabel.setText("Account Status: Savings Balance: $" + balance);
            } else {
                logArea.append("Insufficient balance!\n");
            }
                withdrawField.setText(""); // in this this i use to clear the loan filed

        });

        loanBtn.addActionListener(e -> {
            loanAmount = Double.parseDouble(loanField.getText());
            emi = loanAmount / 12;
            monthsRemaining = 12;
            payEmiField.setText(String.valueOf(emi));
            payEmiBtn.setEnabled(true);
            loanStatus.setText("Loan Status: Loan Amount: $" + loanAmount);
            emiStatus.setText("EMI Status: EMI: $" + emi + " (Remaining: 12 months)");
            logArea.append("Loan Approved: $" + loanAmount + "\n");
                loanField.setText(""); // in this this i use to clear the loan filed 

        });

        payEmiBtn.addActionListener(e -> {
            if (monthsRemaining > 0 && balance >= emi) {
                new Thread(() -> {
                    balance -= emi;
                    loanAmount -= emi;
                    monthsRemaining--;
                    SwingUtilities.invokeLater(() -> {
                        logArea.append("EMI Paid: $" + emi + " | Remaining: " + monthsRemaining + " months\n");
                        statusLabel.setText("Account Status: Savings Balance: $" + balance);
                        loanStatus.setText("Loan Status: Loan Amount: $" + loanAmount);
                        emiStatus.setText("EMI Status: EMI: $" + emi + " (Remaining: " + monthsRemaining + " months)");
                        if (monthsRemaining == 0) {
                            payEmiBtn.setEnabled(false);
                            logArea.append("Loan Fully Paid!\n");
                        }
                    });
                }).start();
            } else {
                logArea.append("Not enough balance or EMI completed!\n");
            }
        });

        frame.setVisible(true);
    }
}
