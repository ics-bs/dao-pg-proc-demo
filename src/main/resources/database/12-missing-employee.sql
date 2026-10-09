-- Both calls deliberately fail; E404 is absent from the example seed.
CALL company.update_employee('E404', 'Missing', 50000);
CALL company.delete_employee('E404');
