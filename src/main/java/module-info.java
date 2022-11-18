module com.example.labproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires zt.exec;
    requires java.logging;


    opens com.example.labproject to javafx.fxml;
    exports com.example.labproject;
}