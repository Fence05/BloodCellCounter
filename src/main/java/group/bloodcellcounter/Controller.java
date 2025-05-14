package group.bloodcellcounter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.Button;
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
    AnchorPane imageAnchor;



    public Image plainImage;

    private ComputerInBack processor = new ComputerInBack();


    public void getImages(ActionEvent actionEvent){
        FileChooser imgChooser = new FileChooser();
        imgChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        imgChooser.setTitle("Open Image");

        File selectedImg = imgChooser.showOpenDialog(imageView.getScene().getWindow()); // ✅ fixed
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
            int[] data = processor.cellsCounter(plainImage);  // Count red and white cells

            redBloodCellCount.setText("Red Count: " + data[0]);
            whiteBloodCellCount.setText("White Count: " + data[1]);
        }
    }

    // Method to highlight and draw boxes around the cells
    public void highlightCells() {
        Image image = plainImage;
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        double viewWidth = imageView.getBoundsInParent().getWidth();
        double viewHeight = imageView.getBoundsInParent().getHeight();
        double scaleX = viewWidth / imageWidth;
        double scaleY = viewHeight / imageHeight;

        // Look for existing canvas or create a new one
        Canvas overlayCanvas = (Canvas) imageAnchor.lookup("#overlayCanvas");
        if (overlayCanvas == null) {
            overlayCanvas = new Canvas(imageWidth, imageHeight);
            overlayCanvas.setId("overlayCanvas");
            imageAnchor.getChildren().add(overlayCanvas);
        }

        GraphicsContext gc = overlayCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, overlayCanvas.getWidth(), overlayCanvas.getHeight());
        gc.setLineWidth(2);

        // Get all cell nodes
        DisjointSetNode<Integer>[] pictureNode = processor.getPictureNode();
        HashMap<DisjointSetNode<Integer>, int[]> cellBoxes = ComputerInBack.boxesSurroundingCells(
                pictureNode, processor.getDisjointSC());

        for (Map.Entry<DisjointSetNode<Integer>, int[]> entry : cellBoxes.entrySet()) {
            int[] box = entry.getValue();

            // box format: {cellType, minX, minY, width, height}
            int cellType = box[0];
            int minX = box[1];
            int minY = box[2];
            int width = box[3];
            int height = box[4];

            // Determine color
            Color boxColor = switch (cellType) {
                case 1 -> Color.RED;
                case 2 -> Color.BLUE;
                default -> Color.PURPLE;
            };

            gc.setStroke(boxColor);
            gc.strokeRect(minX * scaleX, minY * scaleY, width * scaleX, height * scaleY);
        }
    }

}
