CREATE FUNCTION company.uspGetAllEmployees()
RETURNS TABLE (EmpNo VARCHAR, EmpName VARCHAR, EmpSalary NUMERIC)
LANGUAGE sql
AS $$
    SELECT e.EmpNo, e.EmpName, e.EmpSalary
    FROM company.Employee e;
$$;
