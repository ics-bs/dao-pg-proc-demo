CREATE FUNCTION company.uspGetAllEmployeesWithDepartments()
RETURNS TABLE (EmpNo VARCHAR, EmpName VARCHAR, EmpSalary NUMERIC,
               DeptName VARCHAR, DeptBudget NUMERIC)
LANGUAGE sql
AS $$
    SELECT e.EmpNo, e.EmpName, e.EmpSalary,
           d.DeptName, d.DeptBudget
    FROM company.Employee e
    JOIN company.Work w ON e.EmployeeID = w.EmployeeID
    JOIN company.Department d ON w.DepartmentID = d.DepartmentID;
$$;
