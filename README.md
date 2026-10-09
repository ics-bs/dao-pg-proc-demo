# dao-pg-proc-demo

PostgreSQL version of [ics-bs/dao-sp-demo](https://github.com/ics-bs/dao-sp-demo),
matching the INFC20 Java client programming presentation. Java, FXML, and SQL
examples are included in full, including the standalone console demonstrations.

## Requirements

- JDK 21
- Maven 3.9 or later
- PostgreSQL 18 (the version used for validation)
- A desktop environment for the JavaFX application

Maven supplies PostgreSQL JDBC 42.7.13 and JavaFX 21.0.8.

## Project structure

```text
pom.xml
src/main/
  java/
    module-info.java
    se/lu/ics/
      Main.java
      controllers/EmployeesViewController.java
      data/ConnectionHandler.java
      data/DaoException.java
      data/EmployeeDao.java
      models/Employee.java
      models/Department.java
      demos/
        CursorDemo.java
        ResultSetDemo.java
        TransactionDemo.java
        DepartmentsDemo.java
        InsertDemo.java
        UpdateDemo.java
  resources/
    config/
      config.properties.example
      config.properties           # local; ignored by Git
    database/                     # schema, seed, routines, and grants
    fxml/EmployeesView.fxml
```

The Java packages and resource directories follow the original project. The
additional `demos` package contains the console examples from the presentation.
The original repository's unrelated Course/Student schema is replaced by the
`employee`/`department`/`work` schema shown in the presentation.

## SQL naming conventions

SQL follows the INFC20 server-side programming examples: unquoted snake_case
identifiers, uppercase keywords, `p_` parameters, and `v_` local variables.
Constraints include the table and key columns in their names. Queries list
result columns explicitly, use `AS` aliases and qualified column references,
and specify `ASC` for ascending result order. Java identifiers retain Java naming.

| Routine | Kind |
| --- | --- |
| `company.get_all_employees()` | Function |
| `company.get_employee_by_emp_no(p_emp_no)` | Function |
| `company.get_all_employees_with_departments()` | Function |
| `company.insert_employee(p_emp_no, p_emp_name, p_emp_salary)` | Procedure |
| `company.update_employee(p_emp_no, p_emp_name, p_emp_salary)` | Procedure |
| `company.delete_employee(p_emp_no)` | Procedure |

These identifiers replace the earlier `usp` names and mixed-case SQL spellings.
The setup scripts describe a fresh database, not an in-place migration of an
existing installation. Transaction handling, permissions, and error behavior
are unchanged.

## Database setup

Use a fresh, dedicated `companydb` database. The numbered scripts create objects
and roles once; they are not reset scripts. Run setup as the database owner or an
administrator with permission to create roles. Replace `postgres` below if your
administrator has another name.

```bash
psql -U postgres -d postgres -c 'CREATE DATABASE companydb;'
(
  set -e
  for script in src/main/resources/database/0[1-9]-*.sql; do
    psql -U postgres -d companydb -v ON_ERROR_STOP=1 -f "$script"
  done
)
```

Scripts 01–02 create and seed the tables, 03–08 create the read functions and
write procedures, and 09 creates the application role and grants its permissions.
Set its password interactively:

```bash
psql -U postgres -d companydb
```

```text
\password java_app_user
\q
```

The read functions use the caller's permissions, so `java_app_user` receives
`SELECT` on all three tables. The DAO calls procedures for inserts, updates, and
deletes. These procedures explicitly commit or roll back and therefore use
`SECURITY INVOKER`. Their required table-write grants also permit direct writes;
this example does not enforce procedure-only writes at the database boundary.

Keep JDBC auto-commit enabled for those procedure calls. They cannot be called
inside an explicit client transaction. `escapeSyntaxCallMode=call` translates
JDBC `{call ...}` syntax into PostgreSQL `CALL`; ordinary `SELECT` is unaffected.

## Configure and run

```bash
cp src/main/resources/config/config.properties.example \
   src/main/resources/config/config.properties
```

Edit `config.properties` with your host, port, database, user, and local password.
The application loads `/config/config.properties` from the classpath. This local
file is ignored by Git; the example contains only placeholder values.

```bash
mvn clean package
mvn javafx:run
```

The window lists employees and lets you add an employee. The initial seed has
E1–E6. To inspect the JDBC dependency:

```bash
mvn dependency:tree -Dincludes=org.postgresql:postgresql
```

## Console examples

Run the main methods in `se.lu.ics.demos` from your IDE, or build a runtime
classpath and launch them from a POSIX shell:

```bash
mvn compile dependency:build-classpath -Dmdep.outputFile=target/classpath.txt
java -cp "target/classes:$(cat target/classpath.txt)" se.lu.ics.demos.ResultSetDemo
```

Replace the final class name to run another demonstration:

| Class | Configuration and prerequisite |
| --- | --- |
| `ResultSetDemo` | `java_app_user`; prints the seeded employees through the read function. |
| `DepartmentsDemo` | `java_app_user`; reads employees and their departments. |
| `InsertDemo` | `java_app_user`; E8 must be absent. Inserts Sam as E8. |
| `UpdateDemo` | `java_app_user`; run after `InsertDemo`. Updates E8 to Guy. |
| `CursorDemo` | `java_tx_demo`; use the six-row seed. Calls `next()` seven times explicitly to demonstrate cursor movement. |
| `TransactionDemo` | `java_tx_demo`; E1234 and Finance must initially be absent for success. Demonstrates direct SQL and a client-managed transaction. |

For the last two demos, create the separate role and set its password:

```bash
psql -U postgres -d companydb -v ON_ERROR_STOP=1 \
  -f src/main/resources/database/10-transaction-role.sql
psql -U postgres -d companydb
```

```text
\password java_tx_demo
\q
```

Switch the username and password in `config.properties`, then run `mvn compile`
to copy the changed configuration into `target/classes`. Switch back to
`java_app_user` for the application and DAO demos.

For the transaction rollback example, start with Finance present and E1234
absent. The department insert fails and the employee insert is rolled back.
Simply rerunning a successful demo instead fails at the employee insert, since
E1234 already exists. `TransactionDemo` prints the rollback message and then
rethrows the exception, so failure produces a nonzero exit status.

`12-missing-employee.sql` contains two deliberately failing calls for absent E404.
Run the calls separately to observe both custom errors; it is not a setup script.

## Scope of the example

The Java model uses `double` for money and does not preserve SQL NULL in numeric
fields. It also omits `work.start_date`. These match the presentation; exact money
and nullable values need a richer model. The JavaFX launcher retains the original
TODO for more complete startup-error handling.
