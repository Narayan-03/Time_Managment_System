import javax.swing.*;
import java.awt.*;

public class TimeManagementApp extends JFrame {

    public TimeManagementApp() {
        setTitle("TIME MANAGEMENT SYSTEM");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel("TIME MANAGEMENT SYSTEM", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));

        JButton adminButton = new JButton("Admin");
        JButton facultyButton = new JButton("Faculty");
        JButton studentButton = new JButton("Student");

        adminButton.addActionListener(e -> new AdminPanel().setVisible(true));
        studentButton.addActionListener(e -> new StudentManagement().setVisible(true));
        facultyButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Faculty panel is not connected yet.",
                        "Information", JOptionPane.INFORMATION_MESSAGE));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttons.add(adminButton);
        buttons.add(facultyButton);
        buttons.add(studentButton);

        setLayout(new BorderLayout(10, 10));
        add(heading, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimeManagementApp().setVisible(true));
    }
}
