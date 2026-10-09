CREATE FUNCTION company.get_all_employees_with_departments()
RETURNS TABLE (
    emp_no VARCHAR,
    emp_name VARCHAR,
    emp_salary NUMERIC,
    dept_name VARCHAR,
    dept_budget NUMERIC
)
LANGUAGE sql
AS $$
    SELECT
        e.emp_no,
        e.emp_name,
        e.emp_salary,
        d.dept_name,
        d.dept_budget
    FROM company.employee AS e
    JOIN company.work AS w
        ON e.employee_id = w.employee_id
    JOIN company.department AS d
        ON w.department_id = d.department_id;
$$;
