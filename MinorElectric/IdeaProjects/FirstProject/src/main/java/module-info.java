module com.example.firstproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.firstproject to javafx.fxml;
    exports com.example.firstproject;
}