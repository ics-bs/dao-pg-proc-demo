package se.lu.ics.demos;

import java.io.IOException;
import java.util.List;
import se.lu.ics.data.*;
import se.lu.ics.models.*;

public class DepartmentsDemo {
    public static void main(String[] args) {
        try {
            EmployeeDao employeeDao = new EmployeeDao();

            List<Employee> employees = employeeDao.getAllEmployeesWithDepartments();

            for (Employee employee : employees) {

                System.out.println("Employee number: "
                        + employee.getEmployeeNumber());
                System.out.println("Name: " + employee.getName());
                System.out.println("Salary: " + employee.getSalary());
                System.out.println("Departments:");

                for (Department department : employee.getDepartments()) {
                    System.out.println("  Name: " + department.getName());
                    System.out.println("  Budget: " + department.getBudget());
                    System.out.println();
                }
                System.out.println();

            }

        } catch (IOException e) {
            // TODO Error handling
            e.printStackTrace();
        } catch (DaoException e) {
            // TODO Error handling
            e.printStackTrace();
        }
    }
}
