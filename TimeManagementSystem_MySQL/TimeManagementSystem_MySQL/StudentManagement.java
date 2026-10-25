import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentManagement extends JFrame {

    private final JTextField nameField = new JTextField(15);
    private final JTextField rollField = new JTextField(15);
    private final JTextField courseField = new JTextField(15);
    private final DefaultTableModel tableModel;
    private final JTable studentTable;

    public StudentManagement() {
        setTitle("Student Management");
        setSize(800, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel("STUDENT MANAGEMENT", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Roll Number:"));
        formPanel.add(rollField);
        formPanel.add(new JLabel("Course:"));
        formPanel.add(courseField);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Student Name", "Roll Number", "Course"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton addButton = new JButton("Add Student");
        JButton deleteButton = new JButton("Delete Selected");
        JButton clearButton = new JButton("Clear Fields");
        JButton refreshButton = new JButton("Refresh");

        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteSelectedStudent());
        clearButton.addActionListener(e -> clearFields());
        refreshButton.addActionListener(e -> loadStudents());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(heading, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        loadStudents();
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String course = courseField.getText().trim();

        if (name.isEmpty() || roll.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO students (student_name, roll_number, course) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, roll);
            ps.setString(3, course);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student saved to MySQL successfully.");
            clearFields();
            loadStudents();
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "That roll number already exists.",
                    "Duplicate Roll Number", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        String sql = "SELECT id, student_name, roll_number, course FROM students ORDER BY id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("roll_number"),
                        rs.getString("course")
                });
            }
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void deleteSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student row first.");
            return;
        }

        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete the selected student permanently?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.executeUpdate();
            loadStudents();
            JOptionPane.showMessageDialog(this, "Student deleted from MySQL.");
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        courseField.setText("");
        nameField.requestFocusInWindow();
    }

    private void showDatabaseError(SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Could not access the MySQL database.\n\n" +
                "Check that MySQL is running, the database/table exists, and " +
                "DatabaseConnection.java has the correct username and password.\n\n" +
                "Details: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagement().setVisible(true));
    }
}
