module com.example.crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires gson.extras;


    opens com.example.crud to javafx.fxml;
    exports com.example.crud;
    exports com.example.crud.hierarchy;
    opens com.example.crud.hierarchy to javafx.fxml;
    exports com.example.crud.factories;
    opens com.example.crud.factories to javafx.fxml;
}