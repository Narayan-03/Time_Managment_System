CREATE DATABASE IF NOT EXISTS time_management;
USE time_management;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    roll_number VARCHAR(30) NOT NULL UNIQUE,
    course VARCHAR(100) NOT NULL
);

-- Optional: example record for testing
-- INSERT INTO students (student_name, roll_number, course)
-- VALUES ('Aarav Sharma', '101', 'BCA');
