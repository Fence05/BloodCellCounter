package com.example.bloodcellcounter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ComputerInBack {



    public Image ColourConverterButton(Image mainImage){
        System.out.println("image converter button working!");

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

                // Check if the color is within the target ranges (red or purple)
                if ((currentHue <= 50 || currentHue >= 300) || 
                (currentHue >= 190 && currentHue <= 270 && currentSaturation >= 0.15 && currentBrightness >= 0.2)) {
                
                // Adjust hue (keeping it in 0-360 range)
                double newHue = (currentHue + hue) % 360;
                if (newHue < 0) {
                    newHue += 360;
                }

                // Adjust saturation and brightness for matching pixels
                double newSaturation = Math.max(0, Math.min(1, currentSaturation * saturation));
                double newBrightness = Math.max(0, Math.min(1, currentBrightness * brightness));

                // Set new color for matching pixels
                pxWriter.setColor(x, y, Color.hsb(newHue, newSaturation, newBrightness));
            } else {
                // Keep white if no
                pxWriter.setColor(x, y, color);
            }
        }
    }

    return newImg;
}
}