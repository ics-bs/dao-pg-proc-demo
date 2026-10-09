CREATE FUNCTION company.get_employee_by_emp_no(
    p_emp_no VARCHAR
)
RETURNS TABLE (
    emp_no VARCHAR,
    emp_name VARCHAR,
    emp_salary NUMERIC
)
LANGUAGE sql
AS $$
    SELECT
        e.emp_no,
        e.emp_name,
        e.emp_salary
    FROM company.employee AS e
    WHERE
        e.emp_no = p_emp_no;
$$;
