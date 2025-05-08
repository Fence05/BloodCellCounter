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
    Slider saturationSlider;

    @FXML
    Slider brightnessSlider;

    @FXML
    Slider hueSlider;




    public Image plainImage;
    public Stage fileStage;


    @FXML
    private void initialize() {
        //Hue slider (0-360 degrees)
        hueSlider.setMin(0);
        hueSlider.setMax(360);
        hueSlider.setValue(0);

        //Saturation slider (0-1)
        saturationSlider.setMin(0);
        saturationSlider.setMax(1);
        saturationSlider.setValue(1);

        //Brightness slider (0-1)
        brightnessSlider.setMin(0);
        brightnessSlider.setMax(1);
        brightnessSlider.setValue(1);

        // Listeners
        hueSlider.valueProperty().addListener((obs, oldVal, newVal) -> applyCustomImageSlider());
        saturationSlider.valueProperty().addListener((obs, oldVal, newVal) -> applyCustomImageSlider());
        brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> applyCustomImageSlider());
    }

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


    public void applyPlainImage() {
        imageView.setImage(plainImage);
    }

    public void applyCustomImageButton() {
        Image processedImage = new ComputerInBack().ColourConverterButton(plainImage);
        imageView.setImage(processedImage);
    }



    public void applyCustomImageSlider() {
        if (plainImage != null) {  // Add null check to prevent NPE
            double hue = hueSlider.getValue();
            double saturation = saturationSlider.getValue();
            double brightness = brightnessSlider.getValue();

            Image processedImage = new ComputerInBack().ColourConverterSlider(plainImage, hue, saturation, brightness);
            imageView.setImage(processedImage);
        }
    }


}