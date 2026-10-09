-- Fictional teaching data, preserved from the Java source.
INSERT INTO company.department (dept_name, dept_budget)
VALUES ('HR', 150000.00), ('IT', 250000.00), ('Sales', 200000.00);

INSERT INTO company.employee (emp_no, emp_name, emp_salary)
VALUES ('E1', 'Bob', 55000.00), ('E2', 'Dan', 60000.00),
       ('E3', 'Amy', 62000.00), ('E4', 'Ham', 70000.00),
       ('E5', 'Jim', 48000.00), ('E6', 'Sue', 52000.00);

INSERT INTO company.work (employee_id, department_id, start_date)
SELECT
    e.employee_id,
    d.department_id,
    v.start_date
FROM (VALUES
    ('E1', 'HR',    DATE '2023-05-01'),
    ('E1', 'IT',    DATE '2023-06-01'),
    ('E2', 'HR',    DATE '2022-08-15'),
    ('E3', 'IT',    DATE '2021-06-10'),
    ('E4', 'IT',    DATE '2023-01-20'),
    ('E4', 'Sales', DATE '2023-04-25'),
    ('E5', 'Sales', DATE '2022-11-05'),
    ('E6', 'Sales', DATE '2023-03-12')
) AS v (emp_no, dept_name, start_date)
JOIN company.employee AS e
    ON e.emp_no = v.emp_no
JOIN company.department AS d
    ON d.dept_name = v.dept_name;
