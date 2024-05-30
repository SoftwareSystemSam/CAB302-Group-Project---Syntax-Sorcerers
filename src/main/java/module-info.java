/**
 * This module-info.java file is used to define the module and its dependencies.
 */
module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.sun.jna.platform;
    requires com.sun.jna;

//    requires org.junit.jupiter.engine;
//
//    requires org.junit.jupiter.api;
    requires org.mockito;
    requires org.mockito.junit.jupiter;
    requires net.bytebuddy.agent;

    requires java.desktop;

    requires org.kordamp.ikonli.javafx;


    opens com.example.addressbook to javafx.fxml;
    exports com.example.addressbook;
    exports com.example.addressbook.GUI;
    opens com.example.addressbook.GUI to javafx.fxml;
    exports com.example.addressbook.SQL;
    opens com.example.addressbook.SQL to javafx.fxml;

}