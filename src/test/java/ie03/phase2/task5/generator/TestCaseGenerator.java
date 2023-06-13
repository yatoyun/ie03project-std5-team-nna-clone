package ie03.phase2.task5.generator;

import ie03.phase2.task5.Grid;
import ie03.phase2.task5.SolveRoutes;

import java.util.*;

public class TestCaseGenerator {
    private Grid grid;
    private final ArrayList<Object[]> shelves;
    private final ArrayList<Object[]> routes;
    private String intputText;
    private String outputText;

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
        int numShelves = Math.min(3*number, 25);
        int numRoutes = Math.min(3*number, 20);
        grid = new Grid(4*number, 4*number);

        InputCreator inputCr = new InputCreator(grid, shelves, routes);
        OutputCreator outputCr = new OutputCreator(grid, routes);

        inputCr.initializeGenerators(numShelves, numRoutes, number);

        intputText = inputCr.getTestText();
        outputText = outputCr.getTestText();
    }
}
