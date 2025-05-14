package group.bloodcellcounter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ComputerInBack {

    private Set<DisjointSetNode<Integer>> cellRoot = new HashSet<>();
    private DisjointSetController<Integer> DisjointSC = new DisjointSetController<>();
    private DisjointSetNode<Integer>[] pictureNode;

    private static final int RED_VAL = 1;
    private static final int WHITE_VAL = 2;
    private static final int BLANK_VAL = 0;

    public DisjointSetNode<Integer>[] getPictureNode() {
        return pictureNode;
    }

    public DisjointSetController<Integer> getDisjointSC() {
        return DisjointSC;
    }

    public int getWhiteVal() {
        return WHITE_VAL;
    }

    public Image ColourConverterButton(Image mainImage) {
        int width = (int) mainImage.getWidth();
        int height = (int) mainImage.getHeight();
        PixelReader pxReader = mainImage.getPixelReader();

        WritableImage newImg = new WritableImage(width, height);
        PixelWriter pxWriter = newImg.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pxReader.getColor(x, y);
                double hue = color.getHue();
                double saturation = color.getSaturation();
                double brightness = color.getBrightness();

                if ((hue <= 30 || hue >= 300) && saturation >= 0.15 && brightness >= 0.2) {
                    pxWriter.setColor(x, y, Color.RED);
                } else if ((hue >= 200 || hue <= 290) && saturation >= 0.15 && brightness >= 0.2) {
                    pxWriter.setColor(x, y, Color.PURPLE);
                } else {
                    pxWriter.setColor(x, y, Color.WHITE);
                }
            }
        }

        return newImg;
    }

    public Image ColourConverterSlider(Image mainImage, double hueShift, double saturationScale, double brightnessScale) {
        int width = (int) mainImage.getWidth();
        int height = (int) mainImage.getHeight();

        PixelReader pxReader = mainImage.getPixelReader();
        WritableImage newImg = new WritableImage(width, height);
        PixelWriter pxWriter = newImg.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pxReader.getColor(x, y);

                double currentHue = color.getHue();
                double currentSaturation = color.getSaturation();
                double currentBrightness = color.getBrightness();

                boolean isRed = (currentHue <= 50 || currentHue >= 300) && currentSaturation > 0.15;
                boolean isPurple = (currentHue >= 190 && currentHue <= 270) && currentSaturation > 0.15;

                if (isRed || isPurple) {
                    double newHue = (currentHue + hueShift) % 360;
                    if (newHue < 0) {
                        newHue += 360;
                    }

                    double newSaturation = Math.max(0, Math.min(1, currentSaturation * saturationScale));
                    double newBrightness = Math.max(0, Math.min(1, currentBrightness * brightnessScale));

                    pxWriter.setColor(x, y, Color.hsb(newHue, newSaturation, newBrightness));
                } else {
                    pxWriter.setColor(x, y, Color.WHITE);
                }
            }
        }

        return newImg;
    }

    public int[] cellsCounter(Image baseImage) {
        Image image = ColourConverterButton(baseImage);
        int imgWidth = (int) image.getWidth();
        int imgHeight = (int) image.getHeight();

        DisjointSetController<Integer> DSC = new DisjointSetController<>();
        pictureNode = new DisjointSetNode[imgWidth * imgHeight];

        PixelReader pReader = image.getPixelReader();
        int currPixel = 0;

        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                pictureNode[currPixel] = new DisjointSetNode<>(null, x, y);
                Color currColour = pReader.getColor(x, y);
                if (currColour.equals(Color.RED)) {
                    pictureNode[currPixel].setData(RED_VAL);
                } else if (currColour.equals(Color.PURPLE)) {
                    pictureNode[currPixel].setData(WHITE_VAL);
                } else {
                    pictureNode[currPixel].setData(BLANK_VAL);
                }
                currPixel++;
            }
        }

        for (int i = 0; i < pictureNode.length; i++) {
            if (pictureNode[i] != null && pictureNode[i].getData() != BLANK_VAL) {
                int cellVal = pictureNode[i].getData();

                if ((i % imgWidth) != (imgWidth - 1) && (i + 1) < pictureNode.length && pictureNode[i + 1] != null
                        && pictureNode[i + 1].getData() == cellVal) {
                    DSC.unionBySize(pictureNode[i], pictureNode[i + 1]);
                }

                if ((i + imgWidth) < pictureNode.length && pictureNode[i + imgWidth] != null
                        && pictureNode[i + imgWidth].getData() == cellVal) {
                    DSC.unionBySize(pictureNode[i], pictureNode[i + imgWidth]);
                }
            }
        }

        cellRoot.clear();
        for (DisjointSetNode<Integer> node : pictureNode) {
            if (node != null && node.getData() != BLANK_VAL) {
                DisjointSetNode<Integer> root = DSC.findNodeParent(node);
                cellRoot.add(root);
            }
        }

        int redCells = 0;
        int whiteCells = 0;
        for (DisjointSetNode<Integer> node : cellRoot) {
            if (node.getData() == RED_VAL) {
                redCells++;
            } else {
                whiteCells++;
            }
        }

        return new int[]{redCells, whiteCells};
    }

    public static HashMap<DisjointSetNode<Integer>, int[]> boxesSurroundingCells(
            DisjointSetNode<Integer>[] pictureNode,
            DisjointSetController<Integer> disjointSC
    ) {
        HashMap<DisjointSetNode<Integer>, int[]> boxInfoMap = new HashMap<>();

        for (DisjointSetNode<Integer> node : pictureNode) {
            if (node != null && node.getData() != BLANK_VAL) {
                DisjointSetNode<Integer> root = disjointSC.findNodeParent(node);

                int[] box = boxInfoMap.getOrDefault(root, new int[]{
                        node.getData(),
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MIN_VALUE,
                        Integer.MIN_VALUE
                });

                int x = node.getX();
                int y = node.getY();

                box[1] = Math.min(box[1], x);
                box[2] = Math.min(box[2], y);
                box[3] = Math.max(box[3], x);
                box[4] = Math.max(box[4], y);

                boxInfoMap.put(root, box);
            }
        }

        for (DisjointSetNode<Integer> root : boxInfoMap.keySet()) {
            int[] box = boxInfoMap.get(root);
            int minX = box[1];
            int minY = box[2];
            int maxX = box[3];
            int maxY = box[4];
            int width = maxX - minX;
            int height = maxY - minY;

            int[] data = new int[]{box[0], minX, minY, width, height};
            boxInfoMap.put(root, data);
        }

        return boxInfoMap;
    }


}