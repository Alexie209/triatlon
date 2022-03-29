module com.example.triatlon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.apache.logging.log4j;


    opens com.example.triatlon to javafx.fxml;
    exports com.example.triatlon;
    opens com.example.triatlon.domain to java.base;
    exports com.example.triatlon.domain;
}