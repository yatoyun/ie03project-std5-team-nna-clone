package ie03.phase2.task5.generator;

import ie03.phase2.task5.Grid;
import ie03.phase2.task5.SolveRoutes;
import ie03.phase2.task6.generator.OutputWithRouteCreator;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class TestCaseGenerator {
    private Grid grid;
    private final ArrayList<Object[]> shelves;
    private final ArrayList<Object[]> routes;
    private String intputText;
    private String outputText;

    int number;


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

    public void setTestText(int number){
        this.number = number;
        grid = new Grid(4*number, 4*number);

        initilizeCreators();
    }

    public void runGenerator(){
        int numShelves = Math.min(3*number, 25);
        int numRoutes = Math.min(3*number, 20);
        inputCr.initializeGenerators(numShelves, numRoutes, number);

        intputText = inputCr.getTestText();

        try {
            outputText = outputCr.getTestText();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void initilizeCreators(){
        inputCr = new InputCreator(grid, shelves, routes);
        outputCr = new OutputCreator(grid, routes);
    }

    public void useOutputWithRouteCreator() {
        outputCr = new OutputWithRouteCreator(grid, routes); // 子クラスのインスタンスを代入
    }

}
