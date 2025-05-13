module com.example.bloodcellcounter {
    requires javafx.controls;
    requires javafx.fxml;
    requires jmh.core;


    opens group.bloodcellcounter to javafx.fxml;
    exports group.bloodcellcounter;
}