module dao.pg.proc.demo {
    exports se.lu.ics;

    opens se.lu.ics.controllers to javafx.fxml;
    opens se.lu.ics.models to javafx.base;

    requires java.sql;
    requires org.postgresql.jdbc;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
}
