module org.example.supremium {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.supremium to javafx.fxml;
    exports org.example.supremium;
    exports org.example.supremium.controller;
    opens org.example.supremium.controller to javafx.fxml;
}