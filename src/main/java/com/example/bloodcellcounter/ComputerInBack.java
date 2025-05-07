package com.example.bloodcellcounter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ComputerInBack {



    public Image ColourConverter(Image picImage){

        int width = (int) picImage.getWidth();
        int height = (int) picImage.getHeight();

        PixelReader pxReader = picImage.getPixelReader();
        WritableImage newImg = new WritableImage(width,height);
        PixelWriter pxWriter = newImg.getPixelWriter();

        for(int x = 0; x < width; x++){

        }

        return picImage;
    }

}
