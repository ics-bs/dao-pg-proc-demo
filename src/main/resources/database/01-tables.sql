CREATE SCHEMA company;
REVOKE ALL ON SCHEMA company FROM PUBLIC;

CREATE TABLE company.department (
    department_id  INTEGER         GENERATED ALWAYS AS IDENTITY,
    dept_name      VARCHAR(100)    NOT NULL,
    dept_budget    NUMERIC(19, 2),
    CONSTRAINT pk_department_department_id
        PRIMARY KEY (department_id),
    CONSTRAINT uq_department_dept_name
        UNIQUE (dept_name)
);

CREATE TABLE company.employee (
    employee_id  INTEGER         GENERATED ALWAYS AS IDENTITY,
    emp_no       VARCHAR(10)     NOT NULL,
    emp_name     VARCHAR(50),
    emp_salary   NUMERIC(19, 2),
    CONSTRAINT pk_employee_employee_id
        PRIMARY KEY (employee_id),
    CONSTRAINT uq_employee_emp_no
        UNIQUE (emp_no)
);

CREATE TABLE company.work (
    employee_id    INTEGER,
    department_id  INTEGER,
    start_date     DATE,
    CONSTRAINT pk_work_employee_id_department_id
        PRIMARY KEY (employee_id, department_id),
    CONSTRAINT fk_work_employee_id
        FOREIGN KEY (employee_id)
        REFERENCES company.employee (employee_id) ON DELETE CASCADE,
    CONSTRAINT fk_work_department_id
        FOREIGN KEY (department_id)
        REFERENCES company.department (department_id)
);
