CREATE SCHEMA company;
REVOKE ALL ON SCHEMA company FROM PUBLIC;

CREATE TABLE company.Department (
    DepartmentID  INTEGER        GENERATED ALWAYS AS IDENTITY,
    DeptName      VARCHAR(100)   NOT NULL,
    DeptBudget    DECIMAL(19,2),
    CONSTRAINT PK_Department PRIMARY KEY (DepartmentID),
    CONSTRAINT UQ_Department_DeptName UNIQUE (DeptName)
);

CREATE TABLE company.Employee (
    EmployeeID  INTEGER        GENERATED ALWAYS AS IDENTITY,
    EmpNo       VARCHAR(10)    NOT NULL,
    EmpName     VARCHAR(50),
    EmpSalary   DECIMAL(19,2),
    CONSTRAINT PK_Employee PRIMARY KEY (EmployeeID),
    CONSTRAINT UQ_Employee_EmpNo UNIQUE (EmpNo)
);

CREATE TABLE company.Work (
    EmployeeID    INTEGER,
    DepartmentID  INTEGER,
    StartDate     DATE,
    CONSTRAINT PK_Work PRIMARY KEY (EmployeeID, DepartmentID),
    CONSTRAINT FK_Work_Employee FOREIGN KEY (EmployeeID)
        REFERENCES company.Employee(EmployeeID) ON DELETE CASCADE,
    CONSTRAINT FK_Work_Department FOREIGN KEY (DepartmentID)
        REFERENCES company.Department(DepartmentID)
);
