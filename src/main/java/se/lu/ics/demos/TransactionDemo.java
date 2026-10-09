package se.lu.ics.demos;

import java.io.IOException;
import java.sql.*;
import se.lu.ics.data.ConnectionHandler;

public class TransactionDemo {
    public static void main(String[] args) throws IOException, SQLException {
        ConnectionHandler connectionHandler = new ConnectionHandler();

        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);

            String insertEmployee = "INSERT INTO company.employee"
                    + " (emp_no, emp_name, emp_salary) VALUES (?, ?, ?)";

            String insertDepartment = "INSERT INTO company.department"
                    + " (dept_name, dept_budget) VALUES (?, ?)";

            try (PreparedStatement employee =
                    connection.prepareStatement(insertEmployee);
                 PreparedStatement department =
                    connection.prepareStatement(insertDepartment)) {

                employee.setString(1, "E1234");
                employee.setString(2, "John Doe");
                employee.setDouble(3, 60000.00);
                employee.executeUpdate();

                department.setString(1, "Finance");
                department.setDouble(2, 500000.00);
                department.executeUpdate();

                connection.commit();
                System.out.println("Transaction committed successfully.");
            } catch (SQLException e) {
                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back due to error.");
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            }
        }
    }
}
