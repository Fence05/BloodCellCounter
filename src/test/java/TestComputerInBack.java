
import group.bloodcellcounter.ComputerInBack;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestComputerInBack {
    
    private ComputerInBack computer;
    private Image testImage;

    @BeforeEach
    void setUp() {
        computer = new ComputerInBack();
        // test image 
        WritableImage img = new WritableImage(3, 3);
        PixelWriter writer = img.getPixelWriter();
        writer.setColor(0, 0, Color.RED);
        writer.setColor(0, 1, Color.PURPLE);
        writer.setColor(0, 2, Color.WHITE);
        writer.setColor(1, 0, Color.WHITE);
        writer.setColor(1, 1, Color.PURPLE);
        writer.setColor(1, 2, Color.WHITE);
        writer.setColor(2, 0, Color.RED);
        writer.setColor(2, 1, Color.WHITE);
        writer.setColor(2, 2, Color.PURPLE);
        testImage = img;
    }
    
    @Test
    void testColourConverterButton() {
        Image result = computer.ColourConverterButton(testImage);
        assertNotNull(result, "image should not be null");
        assertEquals(testImage.getWidth(), result.getWidth(), "image width should be same");
        assertEquals(testImage.getHeight(), result.getHeight(), "image height should be same");
        
        // Test pixel colors
        PixelReader reader = result.getPixelReader();
        assertEquals(Color.RED, reader.getColor(0, 0), "red pixel should still be red");
        assertEquals(Color.WHITE, reader.getColor(1, 0), "white pixel should still be white");
    }
    
    @Test
    void testColourConverterSlider() {
        double hueShift = 10.0;
        double saturationScale = 1.2;
        double brightnessScale = 0.8;
        
        Image result = computer.ColourConverterSlider(testImage, hueShift, saturationScale, brightnessScale);
        assertNotNull(result, "Converted image should not be null");
        assertEquals(testImage.getWidth(), result.getWidth(), "Image width should be preserved");
        assertEquals(testImage.getHeight(), result.getHeight(), "Image height should be preserved");
    }
    
    @Test
    void testCellsCounter() {
        int[] counts = computer.cellsCounter(testImage);
        assertNotNull(counts, "Cell counts should not be null");
        assertEquals(2, counts.length, "count for red and white cells");
        assertTrue(counts[0] >= 0, "red blood cell count not less than 0");
        assertTrue(counts[1] >= 0, "white blood cell not less than 0");
    }
    
    @Test
    void testCellsCounterWithEmptyImage() {
        WritableImage emptyImage = new WritableImage(2, 2);
        PixelWriter writer = emptyImage.getPixelWriter();
        // fill with white pixels
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                writer.setColor(x, y, Color.WHITE);
            }
        }
        
        int[] counts = computer.cellsCounter(emptyImage);
        assertEquals(0, counts[0], "find no red blood cells");
        assertEquals(0, counts[1], "find no white blood cells");
    }
}