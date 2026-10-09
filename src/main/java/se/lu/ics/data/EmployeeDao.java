package se.lu.ics.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import se.lu.ics.models.Employee;
import se.lu.ics.models.Department;

public class EmployeeDao {
    private final ConnectionHandler connectionHandler;

    public EmployeeDao() throws IOException {
        connectionHandler = new ConnectionHandler();
    }

    private Employee mapToEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getString("EmpNo"),
                resultSet.getString("EmpName"),
                resultSet.getDouble("EmpSalary"));
    }

    public List<Employee> getAll() {
        String query = "SELECT *"
                + " FROM company.uspGetAllEmployees()"
                + " ORDER BY EmpNo";

        List<Employee> employees = new ArrayList<>();

        try (Connection connection = connectionHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(mapToEmployee(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Error fetching all employees.", e);
        }

        return employees;
    }

    public Employee getByNo(String empNo) {
        String query = "SELECT *"
                + " FROM company.uspGetEmployeeByEmpNo(?)";

        try (Connection connection = connectionHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, empNo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToEmployee(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {

            throw new DaoException("Error fetching employee: " + empNo, e);
        }
    }

    public void save(Employee employee) {
        String call = "{call company.uspInsertEmployee(?, ?, ?)}";

        try (Connection connection = connectionHandler.getConnection();
             CallableStatement statement = connection.prepareCall(call)) {

            statement.setString(1, employee.getEmployeeNumber());
            statement.setString(2, employee.getName());
            statement.setBigDecimal(3, BigDecimal.valueOf(employee.getSalary()));

            statement.execute();
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new DaoException(
                        "An employee with this Employee No already exists.", e);
            }

            throw new DaoException("Error saving employee: "
                    + employee.getEmployeeNumber(), e);
        }
    }

    public void update(Employee employee) {
        String call = "{call company.uspUpdateEmployee(?, ?, ?)}";

        try (Connection connection = connectionHandler.getConnection();
             CallableStatement statement = connection.prepareCall(call)) {

            statement.setString(1, employee.getEmployeeNumber());
            statement.setString(2, employee.getName());
            statement.setBigDecimal(3, BigDecimal.valueOf(employee.getSalary()));

            statement.execute();
        } catch (SQLException e) {
            if ("P2001".equals(e.getSQLState())) {
                throw new DaoException("Employee " + employee.getEmployeeNumber()
                        + " does not exist.", e);
            }

            throw new DaoException("Error updating employee: "
                    + employee.getEmployeeNumber(), e);
        }
    }

    public void deleteByNo(String empNo) {
        String call = "{call company.uspDeleteEmployee(?)}";

        try (Connection connection = connectionHandler.getConnection();
             CallableStatement statement = connection.prepareCall(call)) {

            statement.setString(1, empNo);

            statement.execute();
        } catch (SQLException e) {
            if ("P2001".equals(e.getSQLState())) {
                throw new DaoException(
                        "Employee " + empNo + " does not exist.", e);
            }

            throw new DaoException("Error deleting employee: " + empNo, e);
        }
    }

    public List<Employee> getAllEmployeesWithDepartments() {
        String query = "SELECT *"
                + " FROM company.uspGetAllEmployeesWithDepartments()"
                + " ORDER BY EmpNo, DeptName";

        Map<String, Employee> employeeMap = new LinkedHashMap<>();

        try (Connection connection = connectionHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String empNo = resultSet.getString("EmpNo");
                Employee employee = employeeMap.get(empNo);

                if (employee == null) {
                    employee = mapToEmployee(resultSet);
                    employeeMap.put(empNo, employee);
                }

                Department department = new Department(
                        resultSet.getString("DeptName"),
                        resultSet.getDouble("DeptBudget"));
                employee.getDepartments().add(department);
            }
        } catch (SQLException e) {

            throw new DaoException("Error fetching employees and departments.", e);
        }

        return new ArrayList<>(employeeMap.values());
    }
}
