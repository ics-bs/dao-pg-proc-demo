package se.lu.ics.demos;

import java.io.IOException;
import java.util.List;
import se.lu.ics.data.*;
import se.lu.ics.models.*;

public class UpdateDemo {
    public static void main(String[] args) {
        try {
            EmployeeDao employeeDao = new EmployeeDao();

            Employee foundEmployee = employeeDao.getByNo("E8");

            if (foundEmployee == null) {
                throw new DaoException(
                        "Employee E8 was not found; run InsertDemo first.");
            }
            foundEmployee.setName("Guy");
            foundEmployee.setSalary(1000000.0);

            employeeDao.update(foundEmployee);

            Employee updatedEmployee = employeeDao.getByNo("E8");
            System.out.println("Employee number: "
                    + updatedEmployee.getEmployeeNumber());
            System.out.println("Name: " + updatedEmployee.getName());
            System.out.println("Salary: " + updatedEmployee.getSalary());

        } catch (IOException e) {
            // TODO Error handling
            e.printStackTrace();
        } catch (DaoException e) {
            // TODO Error handling
            e.printStackTrace();
        }
    }
}
