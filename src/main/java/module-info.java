module com.example.bloodcellcounter {
    requires javafx.controls;
    requires javafx.fxml;
    requires jmh.core;
    requires java.desktop;


    opens group.bloodcellcounter to javafx.fxml;
    exports group.bloodcellcounter;
}