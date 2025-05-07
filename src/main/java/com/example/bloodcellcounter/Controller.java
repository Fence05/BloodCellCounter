package com.example.bloodcellcounter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import java.io.File;

public class Controller {

    @FXML
    ImageView imageView;

    @FXML
    Button loadButton;

    @FXML
    private Slider saturationSlider;

    @FXML
    private Slider brightnessSlider;

    @FXML
    private Slider hueSlider;



    public Image plainImage;
    public Stage fileStage;


    public void getImages(ActionEvent actionEvent){
        FileChooser imgChooser = new FileChooser();
        imgChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        imgChooser.setTitle("Open Image");

        File selectedImg = imgChooser.showOpenDialog(fileStage);
        Image myImage = new Image("file:///"+selectedImg.getAbsolutePath());
        imageView.setImage(myImage);
        plainImage = myImage;

    }





    //   public void applyCustomImage() {
    //       double hueThreshold = hueSlider.getValue();
    //       double saturationThreshold = saturationSlider.getValue();
    //       double brightnessThreshold = brightnessSlider.getValue();
    //
    //       Image processedImage = ComputerInBack.ColourConverter(
    //               plainImage, hueThreshold, saturationThreshold, brightnessThreshold);
    //       imageView.setImage(processedImage);
    //   }

}










