module edu.lawrence.grocerylist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens edu.lawrence.grocerylist to javafx.fxml;
    exports edu.lawrence.grocerylist;
}
