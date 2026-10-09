package se.lu.ics.controllers;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import se.lu.ics.data.DaoException;
import se.lu.ics.data.EmployeeDao;
import se.lu.ics.models.Employee;

public class EmployeesViewController {
    @FXML private TableView<Employee> tableViewEmployee;
    @FXML private TableColumn<Employee, String> tableColumnEmployeeNumber;
    @FXML private TableColumn<Employee, String> tableColumnEmployeeName;
    @FXML private TableColumn<Employee, Double> tableColumnEmployeeSalary;
    @FXML private TextField textFieldEmployeeNumber;
    @FXML private TextField textFieldEmployeeName;
    @FXML private TextField textFieldEmployeeSalary;
    @FXML private Button btnEmployeeAdd;
    @FXML private Label labelErrorMessage;
    private EmployeeDao employeeDao;

    @FXML
    public void initialize() {
        tableColumnEmployeeNumber.setCellValueFactory(
                new PropertyValueFactory<>("employeeNumber"));

        tableColumnEmployeeName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        tableColumnEmployeeSalary.setCellValueFactory(
                new PropertyValueFactory<>("salary"));

        try {
            employeeDao = new EmployeeDao();
            loadEmployees();
        } catch (IOException e) {
            displayErrorMessage("Error initializing database connection: "
                    + e.getMessage());
            btnEmployeeAdd.setDisable(true);
        }
    }

    private void displayErrorMessage(String message) {
        labelErrorMessage.setText(message);
        labelErrorMessage.setStyle("-fx-text-fill: red;");
    }

    private void clearErrorMessage() {
        labelErrorMessage.setText("");
    }

    private void loadEmployees() {
        clearErrorMessage();

        try {
            List<Employee> employeeList = employeeDao.getAll();

            ObservableList<Employee> employeeObservableList =
                    FXCollections.observableArrayList(employeeList);

            tableViewEmployee.setItems(employeeObservableList);
        } catch (DaoException e) {
            displayErrorMessage("Error loading employees: " + e.getMessage());
        }
    }

    @FXML
    private void buttonEmployeeAdd_OnClick(MouseEvent event) {
        clearErrorMessage();

        try {
            String empNo = textFieldEmployeeNumber.getText();
            String empName = textFieldEmployeeName.getText();
            double empSalary = Double.parseDouble(
                    textFieldEmployeeSalary.getText());

            if (!Double.isFinite(empSalary)) {
                throw new NumberFormatException("Salary must be finite");
            }

            Employee newEmployee = new Employee(empNo, empName, empSalary);
            employeeDao.save(newEmployee);
            loadEmployees();

            textFieldEmployeeNumber.clear();
            textFieldEmployeeName.clear();
            textFieldEmployeeSalary.clear();
        } catch (DaoException e) {
            displayErrorMessage(e.getMessage());
        } catch (NumberFormatException e) {
            displayErrorMessage("Invalid salary. Please enter a valid number.");
        }
    }
}
