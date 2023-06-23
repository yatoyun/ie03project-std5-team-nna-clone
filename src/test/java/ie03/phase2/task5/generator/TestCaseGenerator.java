package ie03.phase2.task5.generator;

import ie03.phase2.task5.Grid;
import ie03.phase2.task6.generator.OutputWithRouteCreator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TestCaseGenerator {
    private Grid grid;
    private final ArrayList<Object[]> shelves;
    private final ArrayList<Object[]> routes;
    private String intputText;
    private String outputText;

    int number;

    private BufferedImage image;


    InputCreator inputCr;
    OutputCreator outputCr;

    public TestCaseGenerator() {
        shelves = new ArrayList<>();
        routes = new ArrayList<>();
    }

    public String getIntputText(){
        return intputText;
    }

    public String getOutputText(){
        return outputText;
    }

    public void saveImage(String path) throws IOException {
        ImageIO.write(image, "png", new File(path+".png"));
    }

    public void setTestText(int number){
        this.number = number;
        grid = new Grid(4*number, 4*number);

        initilizeCreators();
    }

    public void runGenerator(){
        int numShelves = Math.min(3*number, 20);
        int numRoutes = Math.min(number, 20);
        int numWayPoints = Math.min(numRoutes, 20);

        // input init
        inputCr.initializeGenerators(numShelves, numRoutes, numWayPoints);

        // create input text
        intputText = inputCr.getTestText();

        // create grid image
        image = StoreImageSave.create(intputText);

        // calc and set distance graph
        grid.getDistGraph();

        // create output text
        outputGetText(outputCr);
    }

    private void initilizeCreators(){
        inputCr = new InputCreator(grid, shelves, routes);
        outputCr = new OutputCreator(grid, routes);
    }

    private void outputGetText(OutputCreator outputCr){
        try {
            outputText = outputCr.getTestText();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public void useOutputWithRouteCreator() {
        outputCr = new OutputWithRouteCreator(grid, routes); // 子クラスのインスタンスを代入
    }

}
