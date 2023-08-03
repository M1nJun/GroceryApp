module com.mycompany.managerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.managerapp to javafx.fxml;
    exports com.mycompany.managerapp;
}
