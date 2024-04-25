module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.sun.jna.platform;
    requires com.sun.jna;


    opens com.example.addressbook to javafx.fxml;
    exports com.example.addressbook;
    exports com.example.addressbook.GUI;
    opens com.example.addressbook.GUI to javafx.fxml;
    exports com.example.addressbook.SQL;
    opens com.example.addressbook.SQL to javafx.fxml;
}