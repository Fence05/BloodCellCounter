//package group.bloodcellcounter;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.PixelWriter;
//import javafx.scene.image.WritableImage;
//import javafx.scene.paint.Color;
//import org.openjdk.jmh.Main;
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.runner.RunnerException;
//import org.openjdk.jmh.annotations.Benchmark;
//
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//@Measurement(iterations = 10)
//@Warmup(iterations = 5)
//@Fork(value = 1)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.MICROSECONDS)
//@State(Scope.Thread)
//public class ImageBenchmark {
//
//    private Image testImage;
//    private ComputerInBack processor;
//
//    @Setup(Level.Invocation)
//    public void setup() {
//        processor = new ComputerInBack();
//        testImage = createRandomTestImage(100, 100); // A 100x100 image
//    }
//
//    private Image createRandomTestImage(int width, int height) {
//        WritableImage img = new WritableImage(width, height);
//        PixelWriter writer = img.getPixelWriter();
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                double randomValue = Math.random();
//                if (randomValue < 0.33) {
//                    writer.setColor(x, y, Color.RED);
//                } else if (randomValue < 0.66) {
//                    writer.setColor(x, y, Color.PURPLE);
//                } else {
//                    writer.setColor(x, y, Color.WHITE);
//                }
//            }
//        }
//        return img;
//    }
//
//    @Benchmark
//    public void cellCountingBenchmark() {
//        processor.cellsCounter(testImage);
//    }
//
//    @Benchmark
//    public void colorConverterButtonBenchmark() {
//        processor.ColourConverterButton(testImage);
//    }
//
//    @Benchmark
//    public void colorConverterSliderBenchmark() {
//        processor.ColourConverterSlider(testImage,
//            Math.random() * 360,  // Random hue shift
//            0.5 + Math.random(),  // Random saturation between 0.5 and 1.5
//            0.5 + Math.random()); // Random brightness between 0.5 and 1.5
//    }
//
//    public static void main(String[] args) throws RunnerException, IOException {
//        Main.main(args);
//    }
//}