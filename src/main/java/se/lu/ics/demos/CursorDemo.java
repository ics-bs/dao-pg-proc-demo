package se.lu.ics.demos;

import java.sql.*;
import se.lu.ics.data.ConnectionHandler;

public class CursorDemo {
    public static void main(String[] args) throws Exception {
        String query = "SELECT e.employee_id, e.emp_no, e.emp_name"
                + " FROM company.employee AS e"
                + " ORDER BY e.employee_id ASC";
        ConnectionHandler handler = new ConnectionHandler();
        try (Connection connection = handler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            // Consecutive calls illustrate cursor movement.
            boolean a = resultSet.next();
            System.out.println("Employee: " + resultSet.getString("emp_name"));
            System.out.println("On a row? " + a + "\n");

            boolean b = resultSet.next();
            System.out.println("Employee: " + resultSet.getString("emp_name"));
            System.out.println("On a row? " + b + "\n");

            boolean c = resultSet.next();
            System.out.println("Employee: " + resultSet.getString("emp_name"));
            System.out.println("On a row? " + c + "\n");

            boolean d = resultSet.next();
            System.out.println("Employee: " + resultSet.getString("emp_name"));
            System.out.println("On a row? " + d + "\n");

            boolean e = resultSet.next();
            System.out.println("Employee: " + resultSet.getString("emp_name"));
            System.out.println("On a row? " + e + "\n");

            boolean f = resultSet.next();
            System.out.println("Employee: " + resultSet.getString("emp_name"));
            System.out.println("On a row? " + f + "\n");

            boolean g = resultSet.next();
            System.out.println("On a row? " + g);
        }
    }
}
