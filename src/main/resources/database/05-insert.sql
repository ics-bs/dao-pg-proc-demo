CREATE PROCEDURE company.uspInsertEmployee(
    p_EmpNo VARCHAR, p_EmpName VARCHAR, p_EmpSalary NUMERIC)
LANGUAGE plpgsql
SECURITY INVOKER
AS $$
DECLARE
    error_state TEXT;
    error_message TEXT;
BEGIN
    -- Finish the empty entry transaction before choosing isolation.
    COMMIT;
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
    BEGIN
        INSERT INTO company.Employee (EmpNo, EmpName, EmpSalary)
        VALUES (p_EmpNo, p_EmpName, p_EmpSalary);
    EXCEPTION WHEN OTHERS THEN
        GET STACKED DIAGNOSTICS
            error_state = RETURNED_SQLSTATE,
            error_message = MESSAGE_TEXT;
    END;
    -- Transaction control must be outside the exception block.
    IF error_state IS NULL THEN
        COMMIT;
    ELSE
        ROLLBACK;
        RAISE EXCEPTION USING
            ERRCODE = error_state, MESSAGE = error_message;
    END IF;
END;
$$;
