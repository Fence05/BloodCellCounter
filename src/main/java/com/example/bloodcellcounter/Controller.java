package com.example.bloodcellcounter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    Label redBloodCellCount;

    @FXML
    Label whiteBloodCellCount;




    public Image plainImage;
    public Stage fileStage;

    private ComputerInBack processor = new ComputerInBack();

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

    public void countCells() {
        if (plainImage != null) {  // Make sure an image is loaded
            int[] data = processor.cellsCounter(plainImage);  // Count red and white cells

            //
            redBloodCellCount.setText("Red B Count: " + data[0]);
            whiteBloodCellCount.setText("White Count: " + data[1]);
        }
    }








}