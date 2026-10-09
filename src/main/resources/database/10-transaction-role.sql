-- For the direct-SQL transaction and cursor demonstrations.
CREATE ROLE java_tx_demo LOGIN;
GRANT CONNECT ON DATABASE companydb TO java_tx_demo;
GRANT USAGE ON SCHEMA company TO java_tx_demo;
GRANT INSERT ON company.employee, company.department TO java_tx_demo;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA company TO java_tx_demo;
GRANT SELECT (employee_id, emp_no, emp_name) ON company.employee TO java_tx_demo;
-- Set a local password with psql's \password java_tx_demo.
