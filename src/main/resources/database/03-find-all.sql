CREATE FUNCTION company.get_all_employees()
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
    FROM company.employee AS e;
$$;
