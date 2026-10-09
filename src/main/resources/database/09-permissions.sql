-- Run as the owner/administrator in the dedicated companydb database.
-- Set a local password with psql's \password java_app_user afterwards.
CREATE ROLE java_app_user LOGIN;
GRANT CONNECT ON DATABASE companydb TO java_app_user;
GRANT USAGE ON SCHEMA company TO java_app_user;

REVOKE EXECUTE ON ALL ROUTINES IN SCHEMA company FROM PUBLIC;
GRANT EXECUTE ON FUNCTION company.get_all_employees()
    TO java_app_user;
GRANT EXECUTE ON FUNCTION company.get_employee_by_emp_no(VARCHAR)
    TO java_app_user;
GRANT EXECUTE ON FUNCTION company.get_all_employees_with_departments()
    TO java_app_user;
GRANT EXECUTE ON PROCEDURE
    company.insert_employee(VARCHAR, VARCHAR, NUMERIC)
    TO java_app_user;
GRANT EXECUTE ON PROCEDURE
    company.update_employee(VARCHAR, VARCHAR, NUMERIC)
    TO java_app_user;
GRANT EXECUTE ON PROCEDURE company.delete_employee(VARCHAR)
    TO java_app_user;

-- Read functions use the caller's table permissions.
GRANT SELECT ON company.employee, company.department, company.work
    TO java_app_user;

-- Invoker procedures need these underlying write privileges.
GRANT INSERT (emp_no, emp_name, emp_salary),
      UPDATE (emp_name, emp_salary), DELETE
    ON company.employee TO java_app_user;
GRANT USAGE ON SEQUENCE company.employee_employee_id_seq
    TO java_app_user;
