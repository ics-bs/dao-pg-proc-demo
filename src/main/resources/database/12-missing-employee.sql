-- Both calls deliberately fail; E404 is absent from the example seed.
CALL company.uspUpdateEmployee('E404', 'Missing', 50000);
CALL company.uspDeleteEmployee('E404');
