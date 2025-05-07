module com.example.bloodcellcounter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bloodcellcounter to javafx.fxml;
    exports com.example.bloodcellcounter;
}