package group.bloodcellcounter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

    @FXML
    Label ClusterCellCount;

    @FXML
    AnchorPane imageAnchor;
    @FXML
    Label outlierInfoLabel;


    private int clusterCount = 0;
    public Image plainImage;

    private ComputerInBack processor = new ComputerInBack();


    public void getImages(ActionEvent actionEvent){
        FileChooser imgChooser = new FileChooser();
        imgChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        imgChooser.setTitle("Open Image");

        File selectedImg = imgChooser.showOpenDialog(imageView.getScene().getWindow());
        if (selectedImg != null) {
            Image myImage = new Image("file:///" + selectedImg.getAbsolutePath());
            imageView.setImage(myImage);
            plainImage = myImage;
        }
    }



    public void applyPlainImage() {
        imageView.setImage(plainImage);
    }

    public void applyCustomImageButton() {
        Image processedImage = new ComputerInBack().ColourConverterButton(plainImage);
        imageView.setImage(processedImage);
    }



    public void applyCustomImageSlider() {
        if (plainImage != null) {
            double hue = hueSlider.getValue();
            double saturation = saturationSlider.getValue();
            double brightness = brightnessSlider.getValue();

            Image processedImage = new ComputerInBack().ColourConverterSlider(plainImage, hue, saturation, brightness);
            imageView.setImage(processedImage);
        }
    }


    // Method to count red and white blood cells
    public void countCells() {
        if (plainImage != null) {
            int[] data = processor.cellsCounter(plainImage); // Count red, white, and cluster cells
            
            
            redBloodCellCount.setText("Red Count: " + data[0]);
            whiteBloodCellCount.setText("White Count: " + data[1]);
            ClusterCellCount.setText("Cluster Count: " + clusterCount);

        }
    }

    public void highlightCells() {
        if (processor.getPictureNode() == null) {
            System.err.println("The image has not been processed");
            return;
        }

        redBloodCellCount.setText("");
        whiteBloodCellCount.setText("");
        ClusterCellCount.setText("");
        clusterCount = 0;



        // Convert the image using ColourConverterButton
        Image processedImage = processor.ColourConverterButton(plainImage);

        // Retrieve image and view dimensions
        double imageWidth = processedImage.getWidth();
        double imageHeight = processedImage.getHeight();

        double viewWidth = imageView.getBoundsInParent().getWidth();
        double viewHeight = imageView.getBoundsInParent().getHeight();
        double scaleX = viewWidth / imageWidth;
        double scaleY = viewHeight / imageHeight;

        // Check if there is an existing canvas or create a new one
        Canvas overlayCanvas = (Canvas) imageAnchor.lookup("#overlayCanvas");
        if (overlayCanvas == null) {
            overlayCanvas = new Canvas(viewWidth, viewHeight);
            overlayCanvas.setId("overlayCanvas");
            imageAnchor.getChildren().add(overlayCanvas);
        }

        GraphicsContext gc = overlayCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, overlayCanvas.getWidth(), overlayCanvas.getHeight());
        gc.setLineWidth(2);

        // Retrieve the bounding boxes for the cells
        HashMap<DisjointSetNode<Integer>, int[]> cellBoxes = ComputerInBack.boxesSurroundingCells(
            processor.getPictureNode(), processor.getDisjointSC()
        );


        for (DisjointSetNode<Integer> node : cellBoxes.keySet()) {
            int[] box = cellBoxes.get(node);

            // Extract box properties for readability
            int cellType = box[0];
            int minX = box[1];
            int minY = box[2];
            int width = box[3] + 1;  // Add 1 to include the last pixel
            int height = box[4] + 1;

            // box color in determineBoxColor
            Color boxColor = determineBoxColor(cellType, width, height);

            // Draw the rectangle with the determined color
            gc.setStroke(boxColor);
            gc.strokeRect(minX * scaleX, minY * scaleY, width * scaleX, height * scaleY);
        }

        // Display the cluster count in the ClusterCellCount label
        ClusterCellCount.setText("Cluster Count: " + clusterCount);

        // Bind the overlay canvas to the ImageView's layout and dimensions
        overlayCanvas.layoutXProperty().bind(imageView.layoutXProperty());
        overlayCanvas.layoutYProperty().bind(imageView.layoutYProperty());
        overlayCanvas.widthProperty().bind(imageView.fitWidthProperty());
        overlayCanvas.heightProperty().bind(imageView.fitHeightProperty());
    }

    // Helper method to determine the box color
    private Color determineBoxColor(int cellType, int width, int height) {
        if (cellType == processor.getWhiteVal()) {
            return Color.PURPLE; // Purple cells
        } else if (width > 24 || height > 24) {
            clusterCount++; // Increase cluster count for blue boxes
            return Color.BLUE; // Multi-cell group
        } else {
            return Color.RED; // Default red cells
        }
    }

    public void removeCanvasFromImageView() {
        // Look for a canvas overlay by its ID in the parent container (e.g., AnchorPane)
        Canvas overlayCanvas = (Canvas) imageAnchor.lookup("#overlayCanvas");
        
        // If found, remove it
        if (overlayCanvas != null) {
            imageAnchor.getChildren().remove(overlayCanvas);
            System.out.println("Canvas removed successfully.");
        } else {
            System.out.println("No Canvas found to remove.");
        }
    }





}