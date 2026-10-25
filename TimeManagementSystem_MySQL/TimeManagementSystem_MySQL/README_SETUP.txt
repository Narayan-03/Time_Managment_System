TIME MANAGEMENT SYSTEM — MYSQL SETUP
=====================================

WHAT IS INCLUDED
- TimeManagementApp.java: main dashboard
- AdminPanel.java: admin screen
- StudentManagement.java: student CRUD operations using MySQL
- DatabaseConnection.java: JDBC connection settings
- setup.sql: creates the database and students table

1. INSTALL / START MYSQL
Make sure MySQL Server is installed and running. MySQL Workbench can be used to run SQL.

2. CREATE THE DATABASE AND TABLE
Open MySQL Workbench, connect to your local server, open setup.sql, and execute it.
It creates:
  database: time_management
  table: students

3. SET YOUR MYSQL PASSWORD
Open DatabaseConnection.java and change:
  private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";
to your actual MySQL password. The default username in this file is root.
If your MySQL username is different, update USER too.

4. ADD THE MYSQL JDBC DRIVER
The MySQL Connector/J JAR is included in the lib folder and is added to the
classpath automatically when this folder is opened in VS Code.

In IntelliJ IDEA, add the JAR manually:
  File > Project Structure > Modules > Dependencies > + > JARs or Directories
Select lib/mysql-connector-j-*.jar and apply.
Download Connector/J only from the official MySQL website:
  https://dev.mysql.com/downloads/connector/j/

5. RUN THE PROJECT
Run TimeManagementApp.java.
Click Student to open student management, or Admin > Manage Students.

WHAT NOW WORKS
- Add Student: inserts a record into MySQL.
- On opening/refreshing the screen: reads records from MySQL.
- Delete Selected: deletes the selected record from MySQL.
- Duplicate roll numbers are rejected by the database.
- Records remain after closing and reopening the application.

IMPORTANT
- Do not share your real database password publicly.
- The Faculty and Timetable buttons still show placeholder messages; they are not implemented.
- This version uses the default Java package. Keep these files in the same source folder.
- If your MySQL server uses a different port, update the JDBC URL in DatabaseConnection.java.
