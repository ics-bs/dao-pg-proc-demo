CREATE PROCEDURE company.update_employee(
    p_emp_no VARCHAR,
    p_emp_name VARCHAR,
    p_emp_salary NUMERIC
)
LANGUAGE plpgsql
SECURITY INVOKER
AS $$
DECLARE
    v_error_state TEXT;
    v_error_message TEXT;
BEGIN
    -- Finish the empty entry transaction before choosing isolation.
    COMMIT;
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
    BEGIN
        UPDATE company.employee AS e
        SET emp_name = p_emp_name,
            emp_salary = p_emp_salary
        WHERE
            e.emp_no = p_emp_no;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Employee % does not exist.', p_emp_no
                USING ERRCODE = 'P2001';
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS
                v_error_state = RETURNED_SQLSTATE,
                v_error_message = MESSAGE_TEXT;
    END;
    -- Transaction control must be outside the exception block.
    IF v_error_state IS NULL THEN
        COMMIT;
    ELSE
        ROLLBACK;
        RAISE EXCEPTION USING
            ERRCODE = v_error_state, MESSAGE = v_error_message;
    END IF;
END;
$$;
