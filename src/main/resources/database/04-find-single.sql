CREATE FUNCTION company.uspGetEmployeeByEmpNo(p_EmpNo VARCHAR)
RETURNS TABLE (EmpNo VARCHAR, EmpName VARCHAR, EmpSalary NUMERIC)
LANGUAGE sql
AS $$
    SELECT e.EmpNo, e.EmpName, e.EmpSalary
    FROM company.Employee e
    WHERE e.EmpNo = p_EmpNo;
$$;
