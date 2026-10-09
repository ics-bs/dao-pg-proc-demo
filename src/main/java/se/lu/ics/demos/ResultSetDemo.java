package se.lu.ics.demos;

import java.io.IOException;
import java.sql.*;
import se.lu.ics.data.ConnectionHandler;

public class ResultSetDemo {
    public static void main(String[] args) throws IOException, SQLException {
        ConnectionHandler handler = new ConnectionHandler();
        String query = "SELECT *"
                + " FROM company.uspGetAllEmployees()"
                + " ORDER BY EmpNo";

        try (Connection connection = handler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println("Employee " + resultSet.getString("EmpNo") + ":");
                System.out.println("Name: " + resultSet.getString("EmpName"));
                System.out.println("Salary: " + resultSet.getDouble("EmpSalary"));
                System.out.println();
            }
        }
    }
}
