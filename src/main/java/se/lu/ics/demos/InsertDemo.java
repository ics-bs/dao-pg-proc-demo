package se.lu.ics.demos;

import java.io.IOException;
import java.util.List;
import se.lu.ics.data.*;
import se.lu.ics.models.*;

public class InsertDemo {
    public static void main(String[] args) {
        try {
            EmployeeDao employeeDao = new EmployeeDao();

            Employee newEmployee = new Employee("E8", "Sam", 50000.0);
            employeeDao.save(newEmployee);

            Employee foundEmployee = employeeDao.getByNo("E8");
            System.out.println("Employee number: "
                    + foundEmployee.getEmployeeNumber());
            System.out.println("Name: " + foundEmployee.getName());
            System.out.println("Salary: " + foundEmployee.getSalary());

        } catch (IOException e) {
            // TODO Error handling
            e.printStackTrace();
        } catch (DaoException e) {
            // TODO Error handling
            e.printStackTrace();
        }
    }
}
