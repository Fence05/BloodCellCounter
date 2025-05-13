module com.example.bloodcellcounter {
    requires javafx.controls;
    requires javafx.fxml;


    opens group.bloodcellcounter to javafx.fxml;
    exports group.bloodcellcounter;
}