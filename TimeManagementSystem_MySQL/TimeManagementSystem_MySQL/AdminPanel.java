import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JFrame {

    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel("ADMIN PANEL", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));

        JButton studentsButton = new JButton("Manage Students");
        JButton facultyButton = new JButton("Manage Faculty");
        JButton timetableButton = new JButton("Manage Timetable");

        studentsButton.addActionListener(e -> new StudentManagement().setVisible(true));
        facultyButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Faculty management is not connected yet."));
        timetableButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Timetable management is not connected yet."));

        JPanel buttons = new JPanel(new GridLayout(3, 1, 10, 10));
        buttons.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        buttons.add(studentsButton);
        buttons.add(facultyButton);
        buttons.add(timetableButton);

        setLayout(new BorderLayout(10, 10));
        add(heading, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanel().setVisible(true));
    }
}
