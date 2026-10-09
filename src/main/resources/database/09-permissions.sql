-- Run as the owner/administrator in the dedicated companydb database.
-- Set a local password with psql's \password java_app_user afterwards.
CREATE ROLE java_app_user LOGIN;
GRANT CONNECT ON DATABASE companydb TO java_app_user;
GRANT USAGE ON SCHEMA company TO java_app_user;

REVOKE EXECUTE ON ALL ROUTINES IN SCHEMA company FROM PUBLIC;
GRANT EXECUTE ON FUNCTION company.uspGetAllEmployees()
    TO java_app_user;
GRANT EXECUTE ON FUNCTION company.uspGetEmployeeByEmpNo(VARCHAR)
    TO java_app_user;
GRANT EXECUTE ON FUNCTION company.uspGetAllEmployeesWithDepartments()
    TO java_app_user;
GRANT EXECUTE ON PROCEDURE
    company.uspInsertEmployee(VARCHAR, VARCHAR, NUMERIC)
    TO java_app_user;
GRANT EXECUTE ON PROCEDURE
    company.uspUpdateEmployee(VARCHAR, VARCHAR, NUMERIC)
    TO java_app_user;
GRANT EXECUTE ON PROCEDURE company.uspDeleteEmployee(VARCHAR)
    TO java_app_user;

-- Read functions use the caller's table permissions.
GRANT SELECT ON company.Employee, company.Department, company.Work
    TO java_app_user;

-- Invoker procedures need these underlying write privileges.
GRANT INSERT (EmpNo, EmpName, EmpSalary),
      UPDATE (EmpName, EmpSalary), DELETE
    ON company.Employee TO java_app_user;
GRANT USAGE ON SEQUENCE company.employee_employeeid_seq
    TO java_app_user;
