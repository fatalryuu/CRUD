module com.example.crud {
    uses com.example.crud.plugins.Plugin;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires gson.extras;
    requires junit;
    requires org.apache.commons.codec;

    exports com.example.crud;
    opens com.example.crud to javafx.fxml;

    exports com.example.crud.hierarchy;
    opens com.example.crud.hierarchy to javafx.fxml;

    exports com.example.crud.factories.hierarchy;
    opens com.example.crud.factories.hierarchy to javafx.fxml;

    exports com.example.crud.Tests;
    exports com.example.crud.plugins;

    exports com.example.crud.Utils;
    opens com.example.crud.Utils to javafx.fxml;
}