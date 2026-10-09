package se.lu.ics.demos;

import java.io.IOException;
import java.sql.*;
import se.lu.ics.data.ConnectionHandler;

public class ResultSetDemo {
    public static void main(String[] args) throws IOException, SQLException {
        ConnectionHandler handler = new ConnectionHandler();
        String query = "SELECT e.emp_no, e.emp_name, e.emp_salary"
                + " FROM company.get_all_employees() AS e"
                + " ORDER BY e.emp_no ASC";

        try (Connection connection = handler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println("Employee " + resultSet.getString("emp_no") + ":");
                System.out.println("Name: " + resultSet.getString("emp_name"));
                System.out.println("Salary: " + resultSet.getDouble("emp_salary"));
                System.out.println();
            }
        }
    }
}
