package com.example.bloodcellcounter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ComputerInBack {



    public Image ColourConverterButton(Image mainImage){

        int width = (int) mainImage.getWidth();
        int height = (int) mainImage.getHeight();

        PixelReader pxReader = mainImage.getPixelReader();

        WritableImage newImg = new WritableImage(width,height);
        PixelWriter pxWriter = newImg.getPixelWriter();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){

                Color color = pxReader.getColor(x, y);

                double hue = color.getHue();
                double saturation = color.getSaturation();
                double brightness = color.getBrightness();

                if((hue <= 50 || hue >= 300) && saturation >= 0.15 && brightness >= 0.2){
                    pxWriter.setColor(x, y, Color.RED);
                }
                else if((hue >= 190 || hue <= 270) && saturation >= 0.15 && brightness >= 0.2){
                    pxWriter.setColor(x, y, Color.PURPLE);
                }
                else{
                    pxWriter.setColor(x, y, color.WHITE);
                }
            }

        }

        return newImg;
    }



    public Image ColourConverterSlider(Image mainImage, double hue, double saturation, double brightness) {
        int width = (int) mainImage.getWidth();
        int height = (int) mainImage.getHeight();

        PixelReader pxReader = mainImage.getPixelReader();
        WritableImage newImg = new WritableImage(width, height);
        PixelWriter pxWriter = newImg.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pxReader.getColor(x, y);

                // Get the current color's HSB values
                double currentHue = color.getHue();
                double currentSaturation = color.getSaturation();
                double currentBrightness = color.getBrightness();

                if(((hue <= 50 || hue >= 300)|| ((hue >= 190 || hue <= 270) && saturation >= 0.15 && brightness >= 0.2))){

                // Adjust hue
                double newHue = ((currentHue + hue) % 360 + 360) % 360;

                // Simply use the saturation value from the slider
                double newSaturation = currentSaturation * saturation;
                newSaturation = clamp(newSaturation, 0, 1);

                // Similarly for brightness
                double newBrightness = currentBrightness * brightness;
                newBrightness = clamp(newBrightness, 0, 1);

                // Create and set the new color
                pxWriter.setColor(x, y, Color.hsb(newHue, newSaturation, newBrightness));
            }}
        }

        return newImg;
    }

    // Helper method to clamp values between min and max
    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }
}