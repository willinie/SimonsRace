module com.wu.simonsrace {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wu.simonsrace to javafx.fxml;
    exports com.wu.simonsrace;
}