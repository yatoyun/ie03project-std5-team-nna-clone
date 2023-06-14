package ie03.phase2.task6.generator;

import ie03.phase2.task5.Grid;
import ie03.phase2.task5.generator.OutputCreator;
import ie03.phase2.task5.generator.TSPTEST;

import java.util.ArrayList;

public class OutputWithRouteCreator extends OutputCreator {
    public OutputWithRouteCreator(Grid grid, ArrayList<Object[]> routes) {
        super(grid, routes);
    }

    @Override
    public String getOutput(TSPTEST tsp){
        return tsp.getMinRouteValue() + " " + String.join(" ",tsp.getMinRoutePath()) + "\n";
    }
}
