import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class swing {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Greeting App");
        JLabel label = new JLabel("Enter your name:");
        label.setBounds(50, 100, 200, 50);

        JTextField textField = new JTextField();
        textField.setBounds(50, 150, 200, 30);

        JButton button = new JButton("Greet");
        button.setBounds(100, 200, 100, 30);

        JLabel greetingLabel = new JLabel();
        greetingLabel.setBounds(50, 250, 300, 30);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                if (!name.isEmpty()) {
                    greetingLabel.setText("hello " + name + "!");
                } else {
                    greetingLabel.setText("please enter your name");
                }

            }
        });
        frame.add(label);
        frame.add(textField);
        frame.add(button);
        frame.add(greetingLabel);

        frame.setSize(400, 500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}